package com.chat.chat_tradutor.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Column;

import java.time.LocalDateTime;
//import java.time.LocalDate;

import java.time.temporal.ChronoUnit;

@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // para MySQL
    private long id;
    private String name;
    private String email;
    private String password;
    private String nationality;

    @Column(nullable = false, updatable = false)
    private LocalDateTime registrationDate;

    public User() {
    }

    public User(String name, String email, String password, String nationality) {

        this.name = name;
        this.email = email;
        this.password = password;
        this.nationality = nationality;

    }

    @PrePersist()
    public void prePersist() {

        this.registrationDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    }

    public long getId() {

        return id;
    }

    public void setId(long id) {

        this.id = id;

    }

    public String getName() {

        return name;

    }

    public void setName(String name) {

        this.name = name;

    }

    public String getEmail() {

        return email;

    }

    public void setEmail(String email) {

        this.email = email;

    }

    public String getPassword() {

        return password;

    }

    public void setPassword(String password) {

        this.password = password;

    }

    public String getNationality() {

        return nationality;

    }

    public void setNationality(String nationality) {

        this.nationality = nationality;

    }

    public LocalDateTime getRegistrationDate() {

        return registrationDate;

    }

}
