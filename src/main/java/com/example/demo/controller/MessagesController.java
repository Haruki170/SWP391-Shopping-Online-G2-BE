package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagesController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @MessageMapping
    public void sendMessage(String message) {
        // Broadcast the message to all connected users
        messagingTemplate.convertAndSend("/topic/messages", message);
    }


}
