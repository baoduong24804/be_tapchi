package com.be.tapchi.pjtapchi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.be.tapchi.pjtapchi.config.NotificationHandler;
import com.be.tapchi.pjtapchi.controller.websocket.NotificationMessage;

@Service
public class NotificationService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendNotificationToUser(String username, NotificationMessage message) {
        messagingTemplate.convertAndSendToUser(
            username, 
            "/topic/private-notifications", 
            message
        );
    }

    public void broadcastNotification(NotificationMessage message) {
        messagingTemplate.convertAndSend(
            "/topic/notifications", 
            message
        );
    }
}

