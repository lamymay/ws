package com.example.wsclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.util.MimeType;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class WebSocketClient {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        StandardWebSocketClient standardWebSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(standardWebSocketClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        stompClient.setDefaultHeartbeat(new long[]{0, 0});

        ListenableFuture<StompSession> connect = stompClient.connect("ws://127.0.0.1:8080/ws",
                new StompSessionHandler() {
                    @Override
                    public Type getPayloadType(StompHeaders headers) {
                        if (headers != null && headers.getContentType() != null && headers.getContentType().getType() != null) {
                            System.out.println("getContentType=" + headers.getContentType().getType());
//                            if (headers.getContentType().getType().contains("text/plain")) {
//                                return String.class;
//                            }
                        }
                        // 默认返回字节数组类型，如果无法识别消息类型
                        return Object.class;
                    }

                    @Override
                    public void handleFrame(StompHeaders headers, Object payload) {
                        String decode = new String((byte[]) payload);
                        System.out.println("1 Received response from server(decode): " + decode);
                    }

                    @Override
                    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {

                    }

                    @Override
                    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
                        exception.printStackTrace();

                    }

                    @Override
                    public void handleTransportError(StompSession session, Throwable exception) {
                        exception.printStackTrace();

                    }
                });


        // 在客户端成功连接之后，添加订阅逻辑
        connect.addCallback(new ListenableFutureCallback<StompSession>() {
            @Override
            public void onFailure(Throwable ex) {
                // 处理连接失败
                ex.printStackTrace();
            }

            @Override
            public void onSuccess(StompSession session) {
                // 连接成功后，订阅 /topic/hello/2/response
                session.subscribe("/topic/hello/2/response", new StompFrameHandler() {
                    @Override
                    public Type getPayloadType(StompHeaders headers) {
                        MimeType contentType = headers.getContentType();
                        System.out.println(contentType);//application/json
                        return HashMap.class;   // 返回期望的消息类型
                    }

                    @Override
                    public void handleFrame(StompHeaders headers, Object payload) {
                        try {

                            // 处理从服务器接收的消息
                            System.out.println("getClass=" + payload.getClass());
                            System.out.println("onSuccess Received response from server: " + new ObjectMapper().writeValueAsString(payload));
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                });
            }

        });


        StompSession session = connect.get();

        while (!session.isConnected()) {
            Thread.sleep(20);
            System.out.println("socket 没有连接成功 " + session.isConnected());

        }
        System.out.println("socket 连接成功!");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter a message (or 'exit' to quit): ");
            String input = scanner.nextLine();
            if ("exit".equalsIgnoreCase(input)) {
                break;
            }
            // 发送用户输入的消息
            session.send("/app/hello/2", input);

        }
    }


}


