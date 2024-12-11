package com.be.tapchi.pjtapchi.controller.websocket;

import lombok.Data;

@Data
public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

    // Constructors, getters, setters
}
