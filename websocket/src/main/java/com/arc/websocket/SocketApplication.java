package com.arc.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author may
 */
@RestController
@SpringBootApplication
public class SocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocketApplication.class, args);
    }


    @GetMapping("/info")
    public String info() {
        return "info";
    }

}


