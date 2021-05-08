package com.badger.spring.boot.websocket.stomp.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/receive/{id}")
    public String say(@Payload String message, @DestinationVariable String id) throws Exception {
        System.out.println("接收消息-->" + message);
        messagingTemplate.convertAndSend("/subject-topic" + "/" + id, message);
        return "接收成功!-->" + message;
    }
}
