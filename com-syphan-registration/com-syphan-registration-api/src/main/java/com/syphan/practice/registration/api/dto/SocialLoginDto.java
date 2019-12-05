package com.syphan.practice.registration.api.dto;

import com.syphan.practice.registration.api.enumClass.SocialChannelEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SocialLoginDto {
    @NotBlank
    private SocialChannelEnum channel;

    @NotBlank
    private String accessToken;
}
