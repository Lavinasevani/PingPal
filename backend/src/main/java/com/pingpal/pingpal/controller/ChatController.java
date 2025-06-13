package com.pingpal.pingpal.controller;

import com.pingpal.pingpal.dto.MessageDTO;
import com.pingpal.pingpal.model.Message;
import com.pingpal.pingpal.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public Message sendMessage(@RequestBody MessageDTO messageDTO) {
        Message message = new Message(
                messageDTO.getSenderId(),
                messageDTO.getReceiverId(),
                messageDTO.getContent()
        );
        return messageService.saveMessage(message);
    }

    @GetMapping("/messages")
    public List<Message> getMessages(
            @RequestParam Long senderId,
            @RequestParam Long receiverId
    ) {
        return messageService.getMessagesBetween(senderId, receiverId);
    }
}
