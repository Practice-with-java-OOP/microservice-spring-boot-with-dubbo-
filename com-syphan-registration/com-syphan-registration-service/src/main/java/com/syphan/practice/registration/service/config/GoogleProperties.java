package com.syphan.practice.registration.service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app.social.google")
public class GoogleProperties extends BaseSocial {

    private List<String> clientIds;

}
