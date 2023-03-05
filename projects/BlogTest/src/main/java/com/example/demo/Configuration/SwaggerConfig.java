package com.example.demo.Configuration;

import io.swagger.annotations.ApiModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.example.Controller"))//指定包
                .paths(PathSelectors.any())
                .build();
    }
    @Bean
    public ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("轻量博客系统后台接口")
                .description("使用springboot创建的博客系统的后台接口")
                .termsOfServiceUrl("http://localhost:8080/blogTest/swagger2.html")
                .build();
    }
}
