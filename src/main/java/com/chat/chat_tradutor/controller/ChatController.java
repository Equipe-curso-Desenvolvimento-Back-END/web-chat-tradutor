package com.chat.chat_tradutor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.SendTo;

@Controller
public class ChatController {

    // Recebe a mensagem do cliente via STOMP no
    // destino /app/room/{roomId}

    @MessageMapping("/room/{roomId}")

    @SendTo("/topic/room/{roomId}")
    public String sendMessage(
        @DestinationVariable Long roomId,
        String message) { // payload (corpo) da mensagem

        // Por enquanto, apenas devolvemos a mensagem recebida para broadcast.
        System.out.println("Mensagem recebida na sala " + roomId + ": " + message);
        return "Mensagem na Sala " + roomId + ": " + message;
    }
}
