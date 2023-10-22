package com.arc.ws.sever;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");// 表示客户端订阅地址的前缀信息，也就是客户端接收服务端消息的地址的前缀信息
        config.setApplicationDestinationPrefixes("/app");// 指服务端接收地址的前缀，意思就是说客户端给服务端发消息的地址的前缀
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")   //这个方法的作用是添加一个服务端点，来接收客户端的连接。
                .setAllowedOriginPatterns("http://127.0.0.1", "http://wstool.js.org/", "https://www.wetools.com")
//                .withSockJS() //withSockJS()的作用是开启SockJS支持，
//                .setInterceptors(new SystemInfoSocketHandshakeInterceptor())
        ;

    }


    @Controller
    public static class WebSocketController {

        @MessageMapping("/hello/1")
        @SendTo("/topic/hello/1/response")
        public Map<String, Object> hell01(String message) {
            System.out.println("server获取到client: " + message);

            String echo = message + " " + LocalDateTime.now();
            HashMap<String, Object> resp = new HashMap<>();
            resp.put("data", echo);
            System.out.println("server响应client:" + resp);
            return resp;
        }

    }
}


