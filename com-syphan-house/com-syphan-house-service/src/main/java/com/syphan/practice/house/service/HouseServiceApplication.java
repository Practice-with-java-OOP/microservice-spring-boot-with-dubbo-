package com.syphan.practice.house.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.syphan.practice.common.service", "com.syphan.practice.house.service"})
@EnableJpaRepositories(basePackages = {"com.syphan.practice.house.dao", "com.syphan.practice.house.dao"})
@EntityScan(basePackages = "com.syphan.practice.house.api")
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
public class HouseServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(HouseServiceApplication.class, args);
    }
}
