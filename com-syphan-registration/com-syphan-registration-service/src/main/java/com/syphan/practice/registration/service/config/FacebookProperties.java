package com.syphan.practice.registration.service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.social.facebook")
public class FacebookProperties extends BaseSocial {

    private String clientId;

}
