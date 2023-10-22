package com.arc.ws;

import com.arc.ws.sever.WebSocketServer1;
import org.java_websocket.server.WebSocketServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebSocketConfig {

    public static String ip = "127.0.0.1";
    public static int port = 9004;

    @Bean
    public WebSocketServer webSocketServer() {
        WebSocketServer1 server = new WebSocketServer1(ip, port);

        return server;
    }
}
