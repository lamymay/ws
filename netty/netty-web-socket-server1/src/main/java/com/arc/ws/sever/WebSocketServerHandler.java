
package com.arc.ws.sever;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

//上述的 WebSocket 服务器示例创建了一个 WebSocketServerHandler 处理器，用于处理接收到的 WebSocket 消息。它简单地将接收到的消息原样返回给客户端。
public class WebSocketServerHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        // 处理 WebSocket 消息
        if (frame instanceof TextWebSocketFrame) {
            String text = ((TextWebSocketFrame) frame).text();
            ctx.channel().writeAndFlush(new TextWebSocketFrame("Received: " + text));
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        // 新客户端连接
        System.out.println("Client connected: " + ctx.channel().id());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        // 客户端断开连接
        System.out.println("Client disconnected: " + ctx.channel().id());
    }
}
