//
//package com.arc.ws.sever;
//
//import io.netty.bootstrap.Bootstrap;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.ChannelOption;
//import io.netty.channel.EventLoopGroup;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioSocketChannel;
//import io.netty.handler.codec.http.HttpObjectAggregator;
//import io.netty.handler.codec.http.HttpRequestEncoder;
//import io.netty.handler.codec.http.HttpResponseDecoder;
//import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler;
//import io.netty.handler.stream.ChunkedWriteHandler;
//
//public class WebSocketClient {
//
//    public static void main(String[] args) throws Exception {
//        EventLoopGroup group = new NioEventLoopGroup();
//
//        try {
//            Bootstrap bootstrap = new Bootstrap();
//            bootstrap.group(group)
//                    .channel(NioSocketChannel.class)
//                    .option(ChannelOption.TCP_NODELAY, true)
//                    .handler(new ChannelInitializer<SocketChannel>() {
//                        @Override
//                        protected void initChannel(SocketChannel ch) throws Exception {
//                            ch.pipeline().addLast("http-request", new HttpRequestEncoder());
//                            ch.pipeline().addLast("http-response", new HttpResponseDecoder());
//                            ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
//                            ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
//                            ch.pipeline().addLast("websocket", new WebSocketClientCompressionHandler());
//                            ch.pipeline().addLast("handler", new WebSocketClientHandler());
//                        }
//                    });
//
//            Channel ch = bootstrap.connect("localhost", 8888).sync().channel();
//
//            // WebSocket 握手和通信
//
//            ch.closeFuture().sync();
//        } finally {
//            group.shutdownGracefully();
//        }
//    }
//}
