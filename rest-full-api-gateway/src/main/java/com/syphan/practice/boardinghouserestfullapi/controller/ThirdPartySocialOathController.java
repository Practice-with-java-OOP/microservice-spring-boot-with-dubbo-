package com.syphan.practice.boardinghouserestfullapi.controller;

import com.syphan.practice.common.rest.security.CustomUserDetailsServiceImpl;
import com.syphan.practice.common.rest.security.JwtTokenProperties;
import com.syphan.practice.common.rest.security.JwtTokenProvider;
import com.syphan.practice.common.rest.security.UserPrincipal;
import com.syphan.practice.common.rest.util.response.OpenApiWithDataResponse;
import com.syphan.practice.registration.api.UserService;
import com.syphan.practice.registration.api.dto.SocialLoginDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(tags = {"Third-party social account authentication Api"})
@RestController
@RequestMapping("/api/v1/oath/social")
public class ThirdPartySocialOathController {

    @Autowired
    private JwtTokenProperties jwtProperties;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Reference
    private UserService userService;

    @ApiOperation("Sign In")
    @PostMapping
    public ResponseEntity<OpenApiWithDataResponse<String>> signIn(@Valid SocialLoginDto reqParam) {
        UserPrincipal userPrincipal = CustomUserDetailsServiceImpl.create(userService.socialLogin(reqParam));
        return ResponseEntity.ok(new OpenApiWithDataResponse<>(String.format("%s%s", jwtProperties.getTokenPrefix(),
                jwtTokenProvider.generateToken(userPrincipal))));
    }
}
