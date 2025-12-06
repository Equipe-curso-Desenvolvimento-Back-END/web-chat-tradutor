package com.chat.chat_tradutor.model;

import java.io.Serializable;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;

import java.time.LocalDateTime;

import java.time.temporal.ChronoUnit;

import java.util.List;
import java.util.ArrayList;


@Entity
public class ThreadChat implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String description;

    @Column(nullable = false, updatable = false)
    private LocalDateTime registrationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_user_id", nullable = false)
    private User creator;

    @OneToMany(mappedBy = "thread", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();

    public ThreadChat() {
    }

    public ThreadChat(String name, String description, Room room, User user) {

        this.name = name;
        this.description = description;
        this.room = room;
        this.creator = creator;

    }

    @PrePersist
    public void prePersit() {

        this.registrationDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    }

    public long getId() {

        return id;

    }

    public String getName() {

        return name;

    }

    public void setName(String name) {

        this.name = name;

    }

    public String getDescription() {

        return description;

    }

    public void setDescription(String description) {

        this.description = description;

    }

    public LocalDateTime getRegistrationDate() {

        return registrationDate;

    }

    public Room getRoom() {

        return room;

    }

    public User getCreator() {

        return creator;

    }

    public List<Message> getMessages() {

        return messages;

    }

}
