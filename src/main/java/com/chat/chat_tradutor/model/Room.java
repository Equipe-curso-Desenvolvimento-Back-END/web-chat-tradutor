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
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;

import java.time.LocalDateTime;

import java.time.temporal.ChronoUnit;

import java.util.List;
import java.util.ArrayList;

import java.util.Set;
import java.util.HashSet;

@Entity
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String description;
    private Long creatorId;
    private String creatorName;
    private String creatorEmail;
    private String creatorNationality;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(

            name = "room_user",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")

    )

    private Set<User> users = new HashSet<>();

    @Column(nullable = false, updatable = false)
    private LocalDateTime registrationDate;

    public Room() {
    }

    public Room(String name, String description, Set<User> users, Long creatorId, String creatorName, String creatorEmail, String creatorNationality) {

        this.name = name;
        this.description = description;
        this.users = users;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.creatorEmail = creatorEmail;
        this.creatorNationality = creatorNationality;

    }

    @PrePersist()
    public void prePersist() {

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

    // op
    public void setUsers(Set<User> users) {

        this.users = users;

    }

    public Set<User> getUsers() {

        return users;

    }

    public Long getCreatorId() {

        return creatorId;

    }

    public void setCreatorId(Long creatorId) {

        this.creatorId = creatorId;

    }

    public String getCreatorName() {

        return creatorName;

    }

    public void setCreatorName(String creatorName) {

        this.creatorName = creatorName;

    }

    public String getCreatorEmail() {

        return creatorEmail;

    }

    public void setCreatorEmail(String creatorEmail) {

        this.creatorEmail = creatorEmail;

    }

    public String getCreatorNationality() {

        return creatorNationality;

    }

    public void setCreatorNationality(String creatorNationality) {

        this.creatorNationality = creatorNationality;

    }

}
