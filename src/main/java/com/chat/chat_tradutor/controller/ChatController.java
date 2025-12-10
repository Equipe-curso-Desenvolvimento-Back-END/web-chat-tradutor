package com.chat.chat_tradutor.controller;

import com.chat.chat_tradutor.model.Message;

//import com.chat.chat_tradutor.repository.MessageRepository;

import com.chat.chat_tradutor.service.MessageService;

import org.springframework.stereotype.Controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.handler.annotation.Payload;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Controller
public class ChatController {

    @Autowired
    private final MessageService messageService;

    public ChatController( MessageService messageService) {

        this.messageService = messageService;

    }

    // Recebe a mensagem do cliente via STOMP no
    // destino /app/room/{roomId}

    @MessageMapping("/room/{roomId}")

    @SendTo("/topic/room/{roomId}")
    public Message sendMessage(
        @DestinationVariable String roomId,
        @Payload Message message) { // payload real

        // depois adicionar message direto
        Message messageEntity = new Message();

        // manter persistencia no dado

        messageEntity.setRoomId(Long.valueOf(roomId));
        messageEntity.setSenderName(message.getSenderName());
        messageEntity.setContent(message.getContent());
        messageEntity.setTimestamp(LocalDateTime.now());

        // registro no db
        Message messageDB = messageService.saveMessage(messageEntity);

        // retorno de JSON
        return messageDB; // OBJETO JSON completo, objeto Java serealizado de volta para JSON

    }
}
