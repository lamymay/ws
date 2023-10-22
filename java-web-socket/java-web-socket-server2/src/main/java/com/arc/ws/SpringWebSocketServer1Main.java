//package com.arc.ws;
//
//import jakarta.websocket.*;
//import jakarta.websocket.server.ServerEndpoint;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.server.standard.ServerEndpointExporter;
//
//import java.io.IOException;
//
//@SpringBootApplication
//public class SpringWebSocketServer1Main {
//
//    private static final Logger log = LoggerFactory.getLogger(SpringWebSocketServer1Main.class);
//
//    /**
//     * Tips：在不使用内嵌容器的时候可以不做以上步骤。
//     * 注入ServerEndpointExporter，
//     * 这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint
//     */
//    @Bean
//    public ServerEndpointExporter serverEndpointExporter() {
//        return new ServerEndpointExporter();
//    }
//
//
//    public static void main(String[] args) {
//        SpringApplication.run(SpringWebSocketServer1Main.class, args);
//    }
//
////    @Bean
////    public WebMvcConfigurer corsConfigurer() {
////        return new WebMvcConfigurer() {
////            @Override
////            public void addCorsMappings(CorsRegistry registry) {
////                registry.addMapping("/**").allowedOrigins("*");
////            }
////        };
////    }
//
//    @Component
//    @ServerEndpoint("/websocket")
//    public static class MyWebSocketServer {
//
//        @OnOpen
//        public void onOpen(Session session) {
//            System.out.println("WebSocket opened: " + session.getId());
//        }
//
//        @OnMessage
//        public void onMessage(String message, Session session) {
//            System.out.println("Received message: " + message);
//            try {
//                session.getBasicRemote().sendText("You said: " + message + "-" + System.currentTimeMillis());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        @OnClose
//        public void onClose(Session session) {
//            System.out.println("WebSocket closed: " + session.getId());
//        }
//
//        @OnError
//        public void onError(Throwable t) {
//            t.printStackTrace();
//        }
//    }
//}
