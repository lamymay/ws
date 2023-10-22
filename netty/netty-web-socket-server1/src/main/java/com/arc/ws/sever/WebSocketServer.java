package com.arc.ws.sever;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


//在上面的示例中，我们创建了一个 Netty WebSocket 服务器，监听端口 8888，并配置了一些处理器，包括 WebSocket 协议处理器。

public class WebSocketServer {

    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                          .channel(NioServerSocketChannel.class)
                          .option(ChannelOption.SO_BACKLOG, 1024)
                          .handler(new LoggingHandler(LogLevel.INFO))
                          .childHandler(new ChannelInitializer<SocketChannel>() {
                              @Override
                              protected void initChannel(SocketChannel ch) throws Exception {
                                  ch.pipeline().addLast("http-codec", new HttpServerCodec());
                                  ch.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
                                  ch.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
                                  ch.pipeline().addLast("webSocket", new WebSocketServerProtocolHandler("/websocket"));
                                  ch.pipeline().addLast("handler", new WebSocketServerHandler());
                              }
                          });

            Channel ch = serverBootstrap.bind(8888).sync().channel();
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
