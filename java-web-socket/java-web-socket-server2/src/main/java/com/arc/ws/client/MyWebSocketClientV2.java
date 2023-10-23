
package com.arc.ws.client;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import javax.net.ssl.*;
import java.net.URI;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Scanner;


// use wss
public class MyWebSocketClientV2 extends WebSocketClient {

    public MyWebSocketClientV2(URI serverUri) {
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

//SSLContext 是 Java 中用于管理 SSL/TLS 安全通信的类，它负责创建 SSLSocketFactory 和 SSLServerSocketFactory，用于安全连接的建立。在 SSLContext 的 getInstance 方法中，你可以传递不同的协议字符串，包括 "TLS" 和 "SSL"，来获取相应协议的实例。这两者之间的主要区别在于协议版本和推荐的使用。
//
//"TLS"（Transport Layer Security）： 这是推荐的协议，用于建立安全连接。TLS 是 SSL 的后续版本，它包括一系列的协议版本，例如 TLS 1.0、TLS 1.1、TLS 1.2、TLS 1.3 等。TLS 针对 SSL 的一些安全性问题进行了改进，并且通常更加安全和强大。在现代应用程序中，推荐使用 "TLS"。
//
//"SSL"（Secure Sockets Layer）： 这是早期用于建立安全连接的协议。"SSL" 包括 SSL 2.0 和 SSL 3.0 版本。然而，SSL 2.0 和 SSL 3.0 在安全性方面存在一些漏洞，因此不再被推荐使用。TLS 1.0 之后的 TLS 版本被认为更加安全，所以 "SSL" 不再是首选选项。
//
//总之，"TLS" 是更现代和更安全的协议，推荐在安全通信中使用。通常，你应该使用 "TLS" 来创建 SSLContext 实例，除非你特定需要与旧的 SSL 协议版本进行通信，这种情况下你可以使用 "SSL"。在现代应用程序中，强烈建议使用 "TLS" 来确保通信的安全性和兼容性。

    /**
     * 信任任何SSL配置类
     */
    public static class AllowAllSSLSocketClient {

        //获取SSLSocketFactory
        public static SSLSocketFactory getSSLSocketFactory() {
            try {
                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, getTrustManager(), new SecureRandom());
                return sslContext.getSocketFactory();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        //获取TrustManager
        private static TrustManager[] getTrustManager() {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    }
            };
            return trustAllCerts;
        }

        //获取HostnameVerifier，验证主机名
        public static HostnameVerifier getHostnameVerifier() {
            HostnameVerifier hostnameVerifier = (s, sslSession) -> true;
            return hostnameVerifier;
        }

        //X509TrustManager：证书信任器管理类
        public static X509TrustManager getX509TrustManager() {
            X509TrustManager x509TrustManager = new X509TrustManager() {
                //检查客户端的证书是否可信
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) {

                }

                //检查服务器端的证书是否可信
                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };
            return x509TrustManager;
        }
    }

    public static void main(String[] args) throws Exception {
        String serverUri = String.format("wss://127.0.0.1:9000/zero/websocket/1");
        MyWebSocketClientV2 client = new MyWebSocketClientV2(URI.create(serverUri));

        // 创建一个信任所有证书的 TrustManager
        // 初始化 SSLContext
        // 在 WebSocket 客户端中使用这个工厂

        // 创建一个信任所有证书的 SSLContext
        // 在 WebSocket 客户端中使用这个工厂

        // 创建信任所有证书的 SSLContext
        SSLContext sslContext = SSLContext.getInstance("TLS");

        // 创建一个自定义的 TrustManager，用于信任所有证书
        TrustManager[] trustAllCertificates = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
        };


        // 初始化 SSLContext，使用自定义 TrustManager
        sslContext.init(null, trustAllCertificates, null);

        // 在 WebSocket 客户端中使用这个 SSLContext
        client.setSocketFactory(sslContext.getSocketFactory());

        client.connect();
        System.out.println("WebSocket client 已连接 " + serverUri);

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
