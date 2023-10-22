package com.arc.ws;

import com.arc.ws.sever.SpringWebSocketDemo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@SpringBootApplication
public class SpringWebSocketServer2Main {

    private static final Logger log = LoggerFactory.getLogger(SpringWebSocketServer2Main.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringWebSocketServer2Main.class, args);
    }

    //    @Resource
    //    private WebSocket webSocket;
    //
    //    public Object echo(@RequestParam(required = false) String message) {
    //        //创建业务消息信息
    //        Map<String,Object> obj = new HashMap<>();
    //        obj.put("cmd", "topic");//业务类型
    ////        obj.put("msgId", sysAnnouncement.getId());//消息id
    ////        obj.put("msgTxt", sysAnnouncement.getTitile());//消息内容
    //////全体发送
    ////        webSocket.sendAllMessage(obj.toJSONString());
    //////单个用户发送 (userId为用户id)
    ////        webSocket.sendOneMessage(userId, obj.toJSONString());
    //////多个用户发送 (userIds为多个用户id，逗号‘,’分隔)
    ////        webSocket.sendMoreMessage(userIds, obj.toJSONString());
    //
    //        return "echo";
    //    }

    @Resource
    SpringWebSocketDemo webSocket;

    @RequestMapping("echo")
    public Object echo(@RequestParam(required = false) String message) throws JsonProcessingException {
        //创建业务消息信息
        Map<String, Object> contentMap = new HashMap<>();
        contentMap.put("data", message);
        contentMap.put("time", System.currentTimeMillis());
        webSocket.sendAllMessage(new ObjectMapper().writeValueAsString(contentMap));
        return contentMap;
    }


}
