package com.badger.spring.boot.securty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SecurtyApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SecurtyApplication.class, args);
    }

    @GetMapping("/hello")
    public String hello() {
        return "hello word ";
    }

}
