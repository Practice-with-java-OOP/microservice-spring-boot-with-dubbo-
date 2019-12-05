package com.syphan.practice.registration.api;

import com.syphan.practice.common.api.BaseService;
import com.syphan.practice.common.api.exception.BIZException;
import com.syphan.practice.registration.api.dto.AdminCreateUserDto;
import com.syphan.practice.registration.api.dto.SocialLoginDto;
import com.syphan.practice.registration.api.dto.UserCreateDto;
import com.syphan.practice.registration.api.model.User;
import org.springframework.core.io.ByteArrayResource;

public interface UserService extends BaseService<User, Integer> {

    String sendUserSignUpMailCaptcha(String email) throws BIZException;

    User signUp(UserCreateDto data) throws BIZException;

    User adminCreateUser(AdminCreateUserDto data) throws BIZException;

    User findByUsername(String username) throws BIZException;

    ByteArrayResource exportExcel() throws BIZException;

    User socialLogin(SocialLoginDto data) throws BIZException;
}
