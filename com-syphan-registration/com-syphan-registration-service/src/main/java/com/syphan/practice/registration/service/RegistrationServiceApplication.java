package com.syphan.practice.registration.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.syphan.practice.common.service", "com.syphan.practice.registration.service"})
@EnableJpaRepositories(basePackages = "com.syphan.practice.registration.dao")
@EntityScan(basePackages = {"com.syphan.practice.registration.api", "com.syphan.practice.common.api"})
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
public class RegistrationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RegistrationServiceApplication.class, args);
    }

}
