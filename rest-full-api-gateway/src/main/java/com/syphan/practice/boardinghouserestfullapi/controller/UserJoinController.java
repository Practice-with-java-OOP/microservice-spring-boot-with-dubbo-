package com.syphan.practice.boardinghouserestfullapi.controller;

import com.syphan.practice.common.rest.util.EntityValidationUtils;
import com.syphan.practice.common.rest.util.response.OpenApiWithDataResponse;
import com.syphan.practice.registration.api.UserService;
import com.syphan.practice.registration.api.dto.AdminCreateUserDto;
import com.syphan.practice.registration.api.dto.UserCreateDto;
import com.syphan.practice.registration.api.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Api(tags = "User Join Management V1")
@RestController
@RequestMapping("/api/v1/users")
public class UserJoinController {

    @Reference
    private UserService userService;

    @PostMapping("mail-captcha")
    public ResponseEntity<OpenApiWithDataResponse<String>> mailCaptcha(@NotBlank @Email @RequestParam String email) {
        return ResponseEntity.ok(new OpenApiWithDataResponse<>(userService.sendUserSignUpMailCaptcha(email)));
    }

    @ApiOperation("User SignUp")
    @PostMapping
    public ResponseEntity<OpenApiWithDataResponse<User>> signUp(@Valid @RequestBody UserCreateDto reqPram,
                                                                BindingResult bindingResult) {
        EntityValidationUtils.processBindingResults(bindingResult);
        return ResponseEntity.ok(new OpenApiWithDataResponse<User>(userService.signUp(reqPram)));
    }

    @ApiOperation("Admin create user")
    @PreAuthorize("hasAuthority('USER_CREATE')")
    @PostMapping("admin-create")
    public ResponseEntity<OpenApiWithDataResponse<User>> adminCreateUser(@Valid @RequestBody AdminCreateUserDto reqPram,
                                                                         BindingResult bindingResult) {
        EntityValidationUtils.processBindingResults(bindingResult);
        return ResponseEntity.ok(new OpenApiWithDataResponse<User>(userService.adminCreateUser(reqPram)));
    }
}
