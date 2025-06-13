package com.pingpal.pingpal.controller;

import com.pingpal.pingpal.dto.MessageDTO;
import com.pingpal.pingpal.model.Message;
import com.pingpal.pingpal.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MessageService messageService;

    @MessageMapping("/chat")
    public void processMessage(MessageDTO messageDTO) {
        // Save to database
        Message saved = messageService.saveMessage(
                new Message(
                        messageDTO.getSenderId(),
                        messageDTO.getReceiverId(),
                        messageDTO.getContent()
                )
        );

        // Send message to specific user
        messagingTemplate.convertAndSendToUser(
                String.valueOf(messageDTO.getReceiverId()),
                "/queue/messages",
                saved
        );
    }
}
