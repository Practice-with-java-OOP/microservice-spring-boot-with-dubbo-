package com.syphan.practice.common.rest.security;

import com.syphan.practice.registration.api.UserService;
import com.syphan.practice.registration.api.model.Role;
import com.syphan.practice.registration.api.model.User;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@org.apache.dubbo.config.annotation.Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserById(Integer id) throws UsernameNotFoundException {
        User user = userService.getById(id);
        if (user != null) return create(user);
        else throw new UsernameNotFoundException(String.format("%s%s", "User not found with id: ", id));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null) throw new UsernameNotFoundException("username can not be empty.");
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("%s %s", "User not found with username:", username));
        }
        return create(user);
    }

    public static UserPrincipal create(User user) {
        Set<String> roleNameSet = new HashSet<>();
        Set<GrantedAuthority> authorities = user.getRoles().stream().map(Role::getPermissions)
                .flatMap(Collection::stream).collect(Collectors.toSet()).stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getCode())).collect(Collectors.toSet());

        for (Role role : user.getRoles()) {
            roleNameSet.add(role.getCode());
            authorities.add(new SimpleGrantedAuthority(role.getCode()));
        }

        return new UserPrincipal(
                user.getId(),
                user.getUsername(),
                user.getAvatar(),
                user.getPassword(),
                roleNameSet,
                authorities
        );
    }
}
