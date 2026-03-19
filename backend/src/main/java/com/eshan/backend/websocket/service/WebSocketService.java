package com.eshan.backend.websocket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendMessage(String payload) {

        System.out.println("received " + payload);
        //messagingTemplate.convertAndSend("/topic/messages", payload);

        messagingTemplate.convertAndSendToUser(
                "eshan",           // the Principal name from JWT
                "/queue/private",   // destination
                payload
        );

        messagingTemplate.convertAndSendToUser(
                "nimal",           // the Principal name from JWT
                "/queue/private",   // destination
                "hellow nimal"
        );
    }
}
