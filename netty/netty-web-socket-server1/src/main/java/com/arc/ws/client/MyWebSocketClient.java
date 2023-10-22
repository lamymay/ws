
package com.arc.ws.client;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Scanner;


public class MyWebSocketClient extends WebSocketClient {

    public MyWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to the server");
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Received message from server: " + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Connection closed: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public static void main(String[] args) {
        String serverUri = String.format("ws://0.0.0.0:8888");
        MyWebSocketClient client = new MyWebSocketClient(URI.create(serverUri));
        client.connect();
        System.out.println("WebSocket client is connected to " + serverUri);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter a message (or 'exit' to quit): ");
            String input = scanner.nextLine();
            if ("exit".equalsIgnoreCase(input)) {
                client.close();
                break;
            }
            client.send(input);
        }
    }
}
