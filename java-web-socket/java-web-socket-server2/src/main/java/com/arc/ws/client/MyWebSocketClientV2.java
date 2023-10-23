package com.arc.ws.client;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.net.URI;

public class MyWebSocketClientV2 extends WebSocketClient {

    public MyWebSocketClientV2(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to the server");

        // 在连接建立后发送消息
        send("Hello, server!");
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
        String serverUri = "wss://127.0.0.1:9000/websocket/1";
        MyWebSocketClientV2 client = new MyWebSocketClientV2(URI.create(serverUri));

        // 在 WebSocket 客户端连接之前禁用主题备用名称检查
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, null, null);
            SSLSocketFactory factory = sslContext.getSocketFactory();

            client.setSocketFactory(SSLSocketFactory.getDefault());

//            new SSLSocketFactory() {
//                @Override
//                public Socket createSocket(String host, int port) throws IOException {
//                    SSLSocket socket = (SSLSocket) factory.createSocket(host, port);
//                    SSLParameters sslParameters = socket.getSSLParameters();
//                    sslParameters.setEndpointIdentificationAlgorithm("HTTPS");
//                    socket.setSSLParameters(sslParameters);
//                    return socket;
//                }
//            }

            client.connect();
            System.out.println("WebSocket client is connected to " + serverUri);

            // WebSocket 连接建立后会自动发送消息

            // 最后关闭 WebSocket 连接
            // client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

