package com.syphan.practice.common.rest.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.jwt")
public class JwtTokenProperties {
    private String secret;

    private Duration avlPeriod;

    private String header;

    private String tokenPrefix;
}
