package com.chat.chat_tradutor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // O ID da sala (Chave Estrangeira Lógica)
    private Long roomId;

    // Nome do remetente (Nick)
    private String senderName;

    // Conteúdo da mensagem
    private String content;

    // Data e hora de envio
    private LocalDateTime timestamp;

    // Objeto  JSON deve-se ser sempre construtor VAZIO
    public Message() {}

    @PrePersist
    public void prePersist() {

        // Deixando o timezone como no "servidor" hospedado
        this.timestamp = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    }

    public Long getId() {

        return id;

    }

    public void setId(Long id) {

        this.id = id;

    }

    public Long getRoomId() {

        return roomId;

    }
    public void setRoomId(Long roomId) {

        this.roomId = roomId;

    }

    public String getSenderName() {

        return senderName;

    }

    public void setSenderName(String senderName) {

        this.senderName = senderName;

    }

    public String getContent() {

        return content;

    }

    public void setContent(String content) {

        this.content = content;

    }

    public LocalDateTime getTimestamp() {

        return timestamp;

    }

    public void setTimestamp(LocalDateTime timestamp) {

        this.timestamp = timestamp;

    }

}
