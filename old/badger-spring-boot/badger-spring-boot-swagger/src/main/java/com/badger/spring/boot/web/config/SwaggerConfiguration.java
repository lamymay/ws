package com.badger.spring.boot.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * 基于Swagger生成API文档
 * @EnableOpenApi：启动OpenApi的类； 之前是@EnableSwagger2
 * @author: liqi
 */
@Configuration
@EnableOpenApi
public class SwaggerConfiguration {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.badger.spring.boot.web")).paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("我是标题").description("我是描述").contact(new Contact("联系人", "www.baidu.com", "286279186@qq.com"))
                .version("1.0").build();
    }

}