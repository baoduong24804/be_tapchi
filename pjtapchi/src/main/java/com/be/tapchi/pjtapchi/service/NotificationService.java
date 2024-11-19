// package com.be.tapchi.pjtapchi.service;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.messaging.simp.SimpMessagingTemplate;
// import org.springframework.stereotype.Service;

// @Service
// public class NotificationService {

//     @Autowired
//     private SimpMessagingTemplate messagingTemplate;

//     public void sendNotification(String message) {
//         messagingTemplate.convertAndSend("/topic/notifications", message); // Gửi thông báo đến client
//     }
// }
