package com.syphan.practice.common.rest.config;

import com.syphan.practice.common.rest.security.CustomUserDetailsService;
import com.syphan.practice.common.rest.security.CustomUserDetailsServiceImpl;
import com.syphan.practice.common.rest.security.JwtTokenProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtTokenProperties.class)
public class JwtSecurityAutoConfiguration {

//    @Bean
//    @ConditionalOnMissingBean(name = {"customUserDetailsService"})
//    public CustomUserDetailsService customUserDetailsService() {
//        return new CustomUserDetailsServiceImpl();
//    }
}
