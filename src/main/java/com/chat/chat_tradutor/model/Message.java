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

@Entity
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String content;

    @Column(nullable = false, updatable = false)
    private LocalDateTime registrationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thread_id", nullable = false)
    private ThreadChat thread;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_user_id", nullable = false)
    private User author;

    public Message() {
    }

    public Message(String content, ThreadChat thread, User author) {

        this.content = content;
        this.thread = thread;
        this.author = author;

    }

    @PrePersist
    public void prePersit() {

        this.registrationDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    }

    public long getId() {

        return id;

    }

    public String getContent() {

        return content;

    }

    public void setContent(String content) {

        this.content = content;

    }

    public LocalDateTime getRegistrationDate() {

        return registrationDate;

    }

    public ThreadChat getThread() {

        return thread;

    }

    public User getAuthor() {

        return author;

    }

}
