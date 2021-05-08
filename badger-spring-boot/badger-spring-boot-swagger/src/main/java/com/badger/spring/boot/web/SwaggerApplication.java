package com.badger.spring.boot.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@Api(tags = "测试接口")
@RestController
@SpringBootApplication
public class SwaggerApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SwaggerApplication.class, args);
    }

    @Value("${server.port}")
    String port;

    @GetMapping("/demo")
    public String demo() {
        return "我的地址是-->:" + port;
    }
}
