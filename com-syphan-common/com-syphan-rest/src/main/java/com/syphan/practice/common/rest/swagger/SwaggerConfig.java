package com.syphan.practice.common.rest.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.syphan.practice"))
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo())
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Collections.singletonList(apiKey()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Boarding House RestFull API")
                .description("Boarding house project with spring boot, dubbo, and microservice")
                .contact(new Contact("Sy Phan", "http://phantiensy195@gmail.com", "phantiensy195@gmail.com"))
                .version("1.0.1")
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("BearerToken", "Authorization", ApiKeyVehicle.HEADER.getValue());
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("^.*$"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> securityReferences = new ArrayList<>();
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{
                new AuthorizationScope("global", "accessEverything")
        };
        securityReferences.add(new SecurityReference("BearerToken", authorizationScopes));
        return securityReferences;
    }

    @Bean
    public SecurityScheme apiCookieKey() {
        return new ApiKey(HttpHeaders.COOKIE, "apiKey", "cookie");
    }

//    @Bean
//    public SecurityConfiguration security() {
//        return new SecurityConfiguration("client", "secret", "", "",
//                "Bearer access token", ApiKeyVehicle.HEADER, HttpHeaders.AUTHORIZATION, "");
//    }
}
