package com.badger.spring.boot.websocket.stomp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.badger.spring.boot.websocket.stomp.filter.BadgerHandshakeInterceptor;
import com.badger.spring.boot.websocket.stomp.filter.WebSocketChannelInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Bean
    public HandshakeInterceptor badgerHandshakeInterceptor() {
        return new BadgerHandshakeInterceptor();
    }

    /**
     * 拦截器加入 spring ioc容器
     * @return
     */
    @Bean
    public WebSocketChannelInterceptor webSocketChannelInterceptor() {
        return new WebSocketChannelInterceptor();
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/message").setAllowedOrigins("*").addInterceptors(badgerHandshakeInterceptor())
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 这里注册两个，主要是目的是将广播和队列分开。
        registry.enableSimpleBroker("/subject-topic");
    }

    /**
     * 设置输入消息通道的线程数，默认线程为1，可以自己自定义线程数，最大线程数，线程存活时间
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.taskExecutor().corePoolSize(10).maxPoolSize(20).keepAliveSeconds(60);
        registration.interceptors(webSocketChannelInterceptor());
    }
}
