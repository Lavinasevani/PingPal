package com.pingpal.pingpal.service;

import com.pingpal.pingpal.model.Message;
import com.pingpal.pingpal.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> getMessagesBetween(Long senderId, Long receiverId) {
        return messageRepository.findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampAsc(
                senderId, receiverId, senderId, receiverId
        );
    }
}
