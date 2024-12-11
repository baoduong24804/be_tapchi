package com.be.tapchi.pjtapchi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendMessageToAll(String message) {
        messagingTemplate.convertAndSend("/topic/chat", message);
    }
}

