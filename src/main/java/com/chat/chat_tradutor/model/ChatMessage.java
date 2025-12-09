package com.chat.chat_tradutor.model;

import java.io.Serializable;

// Objeto JSON
public class ChatMessage implements Serializable {

    // Indentificador da sala para roteamento
    private String roomId;

    // Conteudo da mensagem original
    private String content;

    // NomeID do usuario que enviou a mensagem
    private String sender;

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

    public String getSender() {

        return sender;

    }

    public void setSender(String sender) {

        this.sender = sender;

    }

    public String getTranslatedContent() {

        return translatedContent;

    }

    public void setTranslatedContent(String translatedContent) {

        this.translatedContent = translatedContent;

    }

}
