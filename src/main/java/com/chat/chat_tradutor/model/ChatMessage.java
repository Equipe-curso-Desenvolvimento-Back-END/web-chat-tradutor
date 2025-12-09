package com.chat.chat_tradutor.model;

import java.io.Serializable;

// Objeto JSON
public class ChatMessage implements Serializable {

    // Indentificador da sala para roteamento
    private String roomId;

    // Conteudo da mensagem original
    private String content;

    // NomeID do usuario que enviou a mensagem
    private String senderName;

    // Conteudo traduzido
    private String translatedContent;


    // Objeto JSON tem que conter construtor VAZIO OBRIGATORIO
    public ChatMessage() {
    }

    public String getRoomId() {

        return roomId;

    }

    public void setRoomId(String roomId) {

        this.roomId = roomId;

    }

    public String getContent() {

        return content;

    }

    public void setContent(String content) {


        this.content = content;

    }

    public String getSenderName() {

        return senderName;

    }

    public void setSenderName(String senderName) {

        this.senderName = senderName;

    }

    public String getTranslatedContent() {

        return translatedContent;

    }

    public void setTranslatedContent(String translatedContent) {

        this.translatedContent = translatedContent;

    }

}
