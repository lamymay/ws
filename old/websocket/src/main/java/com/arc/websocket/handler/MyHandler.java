package com.arc.websocket.handler;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * 相当于controller的处理器
 * 创建websocket服务器
 * 正如springboot 官网推荐的websocket案例,
 * 需要实现WebSocketHandler
 * 或者继承TextWebSocketHandler/BinaryWebSocketHandler当中的任意一个.
 */
public class MyHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        Map<String, String> map = JSONObject.parseObject(payload, HashMap.class);
        System.out.println("=====接受到的数据" + map);
        session.sendMessage(new TextMessage("服务器返回收到的信息," + payload + System.currentTimeMillis()));
    }
}
