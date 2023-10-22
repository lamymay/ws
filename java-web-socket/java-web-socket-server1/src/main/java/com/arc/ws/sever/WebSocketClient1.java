package com.arc.ws.sever;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.Scanner;
import java.util.concurrent.CompletionStage;

public class WebSocketClient1 {

    public static void main(String[] args) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            WebSocket webSocket = client.newWebSocketBuilder()
                    .buildAsync(URI.create("ws://127.0.0.1:9003/websocket"), new WebSocket.Listener() {
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

            // 创建一个线程用于等待用户输入
            Thread inputThread = new Thread(() -> {
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    System.out.print("Enter a message (or 'exit' to quit): ");
                    String input = scanner.nextLine();
                    if ("exit".equalsIgnoreCase(input)) {
                        webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "Client requested to close.");
                        break;
                    }
                    webSocket.sendText(input, true);
                }
            });

            inputThread.start();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
