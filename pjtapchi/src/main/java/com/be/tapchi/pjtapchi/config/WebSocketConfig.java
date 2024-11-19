// package com.be.tapchi.pjtapchi.config;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.messaging.simp.config.MessageBrokerRegistry;
// import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
// import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
// import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

// @Configuration
// @EnableWebSocketMessageBroker
// public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

//     @Override
//     public void configureMessageBroker(MessageBrokerRegistry config) {
//         // Cấu hình Message Broker
//         config.enableSimpleBroker("/topic"); // Đường dẫn gửi thông báo đến client
//         config.setApplicationDestinationPrefixes("/app"); // Đường dẫn nhận thông báo từ client
//     }

//     @Override
//     public void registerStompEndpoints(StompEndpointRegistry registry) {
//         // Cấu hình endpoint cho WebSocket
//         registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS(); // Kết nối qua SockJS (dự phòng)
//     }
// }
