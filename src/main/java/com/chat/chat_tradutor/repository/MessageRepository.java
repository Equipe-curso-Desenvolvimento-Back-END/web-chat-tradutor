package com.chat.chat_tradutor.repository;

import com.chat.chat_tradutor.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByRoomIdOrderByTimestampAsc(Long roomId);
}
