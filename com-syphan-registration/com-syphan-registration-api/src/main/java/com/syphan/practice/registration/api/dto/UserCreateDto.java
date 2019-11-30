package com.syphan.practice.registration.api.dto;

import com.syphan.practice.common.api.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto extends BaseDto {

    @NotBlank(message = "username.must.not.be.blank")
    private String username;

    @NotBlank(message = "password.must.not.be.blank")
    private String password;

    private String avatar;

    private String fullName;

    private String email;

    @NotNull(message = "phoneNum.must.not.be.blank")
    private String phoneNum;
}
