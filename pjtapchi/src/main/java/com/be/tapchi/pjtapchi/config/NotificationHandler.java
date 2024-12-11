package com.be.tapchi.pjtapchi.config;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class NotificationHandler extends TextWebSocketHandler {

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("New session connected: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("Received message: " + message.getPayload());
        for (WebSocketSession s : sessions) {
            if (s.isOpen()) {
                s.sendMessage(new TextMessage("Server received: " + message.getPayload()));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("Session disconnected: " + session.getId());
    }

    public void sendNotification(String message) {
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
