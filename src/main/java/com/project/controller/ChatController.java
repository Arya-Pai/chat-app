package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.project.model.Messages;
import com.project.repo.MessageRepo;

@Controller
public class ChatController {
	private final MessageRepo messageRepo;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ChatController(MessageRepo messageRepo, SimpMessagingTemplate messagingTemplate) {
        this.messageRepo = messageRepo;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(Messages message) {
        messageRepo.save(message);
        // Broadcast to the correct room topic
        messagingTemplate.convertAndSend("/topic/room." + message.getRoomId(), message);
    }
}
