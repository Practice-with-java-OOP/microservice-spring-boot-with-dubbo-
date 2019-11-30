package com.syphan.practice.boardinghouserestfullapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.syphan.practice.common.rest", "com.syphan.practice.boardinghouserestfullapi"})
public class RestApiGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestApiGatewayApplication.class, args);
    }
}
