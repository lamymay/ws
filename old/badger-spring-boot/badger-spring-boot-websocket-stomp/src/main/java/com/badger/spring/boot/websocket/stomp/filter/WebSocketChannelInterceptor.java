package com.badger.spring.boot.websocket.stomp.filter;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebSocketChannelInterceptor implements ChannelInterceptor {

    /**
     * 在消息发送之前调用;
     * 方法中可以对消息进行修改，
     * 如果此方法返回值为空，则不会发生实际的消息发送调用
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel messageChannel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            log.debug("用户连接");
        } else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
            log.debug("用户断开连接");
        } else if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            log.debug("用户订阅消息");
        }
        log.debug("用户操作:{}，指定信息:{}", accessor.getCommand().getMessageType(), accessor.getDestination());
        return message;
    }
}
