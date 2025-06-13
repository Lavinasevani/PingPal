package com.pingpal.pingpal.repository;

import com.pingpal.pingpal.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderIdAndReceiverIdOrReceiverIdAndSenderIdOrderByTimestampAsc(
            Long senderId1, Long receiverId1, Long senderId2, Long receiverId2
    );
}
