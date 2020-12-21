package com.natwest.prime;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.function.Predicate;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.natwest.prime"))
                .paths(PathSelectors.ant("/primes/*"))
                .build();
        docket.useDefaultResponseMessages(false);
        return docket;
    }



    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Prime Number Service API")
                .description("Api returns list of all prime numbers starting from 2 for a given number with top limit of 1000000")
                .build();
    }

}
