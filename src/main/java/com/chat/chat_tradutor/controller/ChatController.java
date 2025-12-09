package com.chat.chat_tradutor.controller;

import com.chat.chat_tradutor.model.ChatMessage; // DTO

import com.chat.chat_tradutor.model.Message;

import com.chat.chat_tradutor.repository.MessageRepository;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.handler.annotation.Payload;


@Controller
public class ChatController {

    private final MessageRepository messageRepository;

    public ChatController(MessageRepository messageRepository) {

        this.messageRepository = messageRepository;

    }

    // Recebe a mensagem do cliente via STOMP no
    // destino /app/room/{roomId}

    @MessageMapping("/room/{roomId}")

    @SendTo("/topic/room/{roomId}")
    public ChatMessage sendMessage(
        @DestinationVariable String roomId,
        @Payload ChatMessage message) { // payload real

        Message messageEntity = new Message();

        // manter persistencia no dado

        messageEntity.setRoomId(Long.valueOf(roomId));
        messageEntity.setSenderName(message.getSenderName());
        messageEntity.setContent(message.getContent());
        messageEntity.setTimestamp(LocalDateTime.now());

        messageRepository.save(messageEntity);

        message.setRoomId(roomId);

        // futuro registro no banco

        return message; // OBJETO JSON completo, objeto Java serealizado de volta para JSON

    }
}
