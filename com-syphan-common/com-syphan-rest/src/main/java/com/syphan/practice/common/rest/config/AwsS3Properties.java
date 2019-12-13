package com.syphan.practice.common.rest.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "aws.s3")
@Getter
@Setter
public class AwsS3Properties {

    private String accessKey;

    private String secretKey;

    private String region;

    private String bucket;

    private String endpoint;
}
