package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.sockjs.SockJsService;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Đăng ký endpoint cố định "/ws"
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("http://localhost:5173") // Chỉ định chính xác origin
                .withSockJS();
               // Không cần cookie phiên nếu không cần

        // Đường dẫn có thể được thêm vào nếu cần thiết
        // Tuy nhiên, bạn sẽ phải sử dụng mã logic khác để xử lý tham số động, thay vì kết nối trực tiếp với "/ws/{id}/websocket".
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Message Broker cho các topics mà client có thể subscribe
        config.enableSimpleBroker("/topic");
        // Prefix cho các message gửi từ client đến server
        config.setApplicationDestinationPrefixes("/app");
    }
}

