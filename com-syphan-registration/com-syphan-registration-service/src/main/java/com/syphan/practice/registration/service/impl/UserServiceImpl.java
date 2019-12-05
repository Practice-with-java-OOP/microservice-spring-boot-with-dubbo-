package com.syphan.practice.registration.service.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.restfb.DefaultFacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.exception.FacebookException;
import com.syphan.practice.common.api.enumclass.ErrType;
import com.syphan.practice.common.api.exception.BIZException;
import com.syphan.practice.common.core.GenerateAvatarUtils;
import com.syphan.practice.common.service.base.BaseServiceImpl;
import com.syphan.practice.registration.api.UserService;
import com.syphan.practice.registration.api.dto.AdminCreateUserDto;
import com.syphan.practice.registration.api.dto.SocialLoginDto;
import com.syphan.practice.registration.api.dto.UserCreateDto;
import com.syphan.practice.registration.api.model.Role;
import com.syphan.practice.registration.api.model.User;
import com.syphan.practice.registration.dao.RoleRepository;
import com.syphan.practice.registration.dao.UserRepository;
import com.syphan.practice.registration.service.config.GoogleProperties;
import com.syphan.practice.registration.service.util.Utils;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@org.apache.dubbo.config.annotation.Service
public class UserServiceImpl extends BaseServiceImpl<User, Integer> implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Autowired
    private GoogleProperties googleProperties;

    @Override
    public String sendUserSignUpMailCaptcha(String email) throws BIZException {
        return null;
    }

    @Transactional
    @Override
    public User signUp(UserCreateDto data) throws BIZException {
        User user = generalCreateUser(data.getUsername(), data.getEmail(), data.getPhoneNum(), data.getPassword(),
                data.getAvatar(), data.getFullName());

        Set<Role> roles = new HashSet<>();
        roles.add(getDefaultUserRole());
        user.setRoles(roles);
        return repository.save(user);
    }

    @Transactional
    @Override
    public User adminCreateUser(AdminCreateUserDto data) throws BIZException {
        User user = generalCreateUser(data.getUsername(), data.getEmail(), data.getPhoneNum(), data.getPassword(),
                data.getAvatar(), data.getFullName());
        Set<Role> roles = new HashSet<>();
        if (!data.getRoleIds().isEmpty()) {
            List<Role> roleList = roleRepository.findAllById(data.getRoleIds());
            if (roleList.size() != new HashSet<>(data.getRoleIds()).size())
                throw BIZException.buildBIZException(ErrType.NOT_FOUND,
                        "role.do.not.existed", String.format("%s%s%s", "Role with id in list Id[ ", data.getRoleIds(), "] do not existed"));
            roles.addAll(roleList);
        } else {
            roles.add(getDefaultUserRole());
        }
        user.setRoles(roles);
        return repository.save(user);
    }

    private User generalCreateUser(String userName, String email, String phoneNum, String password, String avatar, String fullName) {
        if (repository.findByUsername(userName) != null) {
            throw BIZException.buildBIZException(ErrType.CONFLICT,
                    "Signup.Username.Occupied", "The username to be registered is already occupied.");
        }

        if (email != null && repository.findByEmail(email) != null) {
            throw BIZException.buildBIZException(ErrType.CONFLICT,
                    "Signup.Email.Occupied", "The email address to be registered is already occupied.");
        }

        if (repository.findByPhoneNum(phoneNum) != null) {
            throw BIZException.buildBIZException(ErrType.CONFLICT,
                    "Signup.PhoneNumber.Occupied", "The phoneNumber to be registered is already occupied.");
        }

        User user = new User();
        user.setUsername(userName);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setFullName(fullName);
        user.setAvatar(avatar != null ? avatar : GenerateAvatarUtils.generate(email));
        user.setEmail(email);
        user.setPhoneNum(phoneNum);
        return user;
    }

    @Override
    public User findByUsername(String username) throws BIZException {
        return repository.findByUsername(username);
    }

    @Override
    public ByteArrayResource exportExcel() throws BIZException {
        List<User> users = repository.findAll();
        InputStream inputStream = this.getClass().getResourceAsStream("/report_template/export_user.xls");
        Context context = new Context();
        context.putVar("data", users);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ByteArrayResource resource = null;
        try {
            JxlsHelper.getInstance().processTemplate(inputStream, outputStream, context);
            resource = new ByteArrayResource(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resource;
    }

    private Role getDefaultUserRole() {
        Role role = roleRepository.findByCode(Utils.DEFAULT_USER_ROLE);
        if (role != null) {
            return role;
        } else {
            role = new Role();
            role.setName("User");
            role.setCode(Utils.DEFAULT_USER_ROLE);
            return roleRepository.save(role);
        }
    }

    @Override
    @Transactional
    public User socialLogin(SocialLoginDto data) throws BIZException {
        String username = null;
        String name = null;
        String email = null;
        String avatar = null;
        String userSocial = null;

        switch (data.getChannel()) {
            case FACEBOOK: {
                DefaultFacebookClient client = new DefaultFacebookClient(data.getAccessToken(), Version.LATEST);
                try {
                    com.restfb.types.User fbUser = client.fetchObject("me", com.restfb.types.User.class,
                            Parameter.with("fields", "id,name,email,picture"));
                    username = fbUser.getEmail() != null ? fbUser.getEmail() : String.format("%s%s", "fb_", fbUser.getId());
                    name = fbUser.getName();
                    email = fbUser.getEmail();
                    avatar = fbUser.getPicture().getUrl();
                    userSocial = fbUser.getId();
                } catch (FacebookException e) {
                    throw BIZException.buildBIZException(ErrType.CONFLICT, "", "Invalid facebook access token.");
                }
                break;
            }
            case GOOGLE: {
                GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(),
                        JacksonFactory.getDefaultInstance()).setAudience(googleProperties.getClientIds()).build();
                try {
                    if (verifier.verify(data.getAccessToken()) == null)
                        throw new IllegalArgumentException("Invalid ID token");
                    GoogleIdToken idToken = verifier.verify(data.getAccessToken());
                    GoogleIdToken.Payload payload = idToken.getPayload();
                    username = payload.getEmail();
                    if (payload.get("name") != null) {
                        name = payload.get("name").toString();
                    } else {
                        name = String.format("%s%s", "google_", payload.getSubject());
                    }
                    email = payload.getEmail();
                    if (payload.get("picture") != null) {
                        avatar = payload.get("picture").toString();
                    } else {
                        avatar = GenerateAvatarUtils.generate(payload.getEmail());
                    }
                    userSocial = payload.getSubject();
                } catch (Exception e) {
                    throw BIZException.buildBIZException(ErrType.CONFLICT, "", "Invalid facebook access token.");
                }
                break;
            }

            default:
        }
        return userHandler(username, name, email, avatar, userSocial);
    }

    private User userHandler(String username, String name, String email, String avatar, String userSocial) {
        User user;
        if (email == null) {
            user = repository.findByUsername(username);
        } else {
            user = repository.findByEmail(email);
        }
        if (user == null) {
            user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setFullName(name);
            user.setRoles(new HashSet<>(Collections.singletonList(getDefaultUserRole())));
        }
        user.setAvatar(avatar);
        user.setUserSocial(userSocial);
        return repository.save(user);
    }
}
