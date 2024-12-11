package com.be.tapchi.pjtapchi.controller.websocket;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.be.tapchi.pjtapchi.service.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class NotificationController {

    // Gửi thông báo tới một topic cụ thể
    @MessageMapping("/send-notification")
    @SendTo("/topic/notifications")
    public NotificationMessage sendNotification(NotificationMessage message) {
        return message;
    }

    // Gửi thông báo tới một người dùng cụ thể
    @MessageMapping("/private-notification")
    @SendToUser("/topic/private-notifications")
    public NotificationMessage sendPrivateNotification(NotificationMessage message) {
        return message;
    }
}
