// package com.be.tapchi.pjtapchi.controller.user;

// import org.springframework.messaging.handler.annotation.MessageMapping;
// import org.springframework.messaging.handler.annotation.SendTo;
// import org.springframework.stereotype.Controller;

// @Controller
// public class NotificationController {

//     @MessageMapping("/send") // Nhận thông báo từ client (React)
//     @SendTo("/topic/notifications") // Gửi thông báo đến tất cả client
//     public String sendNotification(String message) {
//         return message; // Trả về thông báo
//     }
// }
