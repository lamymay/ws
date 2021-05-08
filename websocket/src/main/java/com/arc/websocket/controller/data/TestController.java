package com.arc.websocket.controller.data;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 控制器
 *
 * @author yechao
 * @date 2018/08/07 15:27
 */
@Slf4j
@RequestMapping("/test")
@RestController
public class TestController {

    @GetMapping("/get/data")
    public Object pull() {
        return System.currentTimeMillis();
    }


}
