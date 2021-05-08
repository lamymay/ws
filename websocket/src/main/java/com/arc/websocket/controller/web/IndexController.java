package com.arc.websocket.controller.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author may
 * @since 2021/5/8 14:34
 */
@Controller
@SpringBootApplication
public class IndexController {

    public static void main(String[] args) {
        SpringApplication.run(com.arc.websocket.SocketApplication.class, args);
    }

    /**
     * 用于跳转测试页面
     *
     * @return String
     */
    @GetMapping("/ws")
    public String ws() {
        return "ws";
    }

//    @GetMapping("/info")
//    public String info() {
//        return "info";
//    }

}


