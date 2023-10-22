package com.example.wsclient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.Scanner;
import java.util.concurrent.CompletionStage;

public class WebSocketClient {

    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        WebSocket webSocket = client.newWebSocketBuilder()
                .buildAsync(URI.create("ws://127.0.0.1:8080/ws"), new WebSocket.Listener() {

                    @Override
                    public void onOpen(WebSocket webSocket) {
                        System.out.println("Connected to server");
                    }

                    @Override
                    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
                        System.out.println("Received message from server: " + data);
                        return null;
                    }

                    @Override
                    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
                        System.out.println("Connection closed: " + reason);
                        return null;
                    }

                    @Override
                    public void onError(WebSocket webSocket, Throwable error) {
                        System.out.println("Error: " + error.getMessage());
                    }
                }).join();

        System.out.println("socket 连接成功!");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter a message (or 'exit' to quit): ");
            String input = scanner.nextLine();
            if ("exit".equalsIgnoreCase(input)) {
                break;
            }
            // 发送用户输入的消息

            // You can send a message like this:
            webSocket.sendText(input, true);

        }
    }
}
