package com.chat.chat_tradutor.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;

import java.time.LocalDateTime;
//import java.time.LocalDate;

import java.time.temporal.ChronoUnit;

import java.util.List;
import java.util.ArrayList;

import java.util.Objects;

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
    private int limitRoom;

    @Column(nullable = false, updatable = false)
    private LocalDateTime registrationDate;

    @ManyToMany(mappedBy = "users")
    private List<Room> rooms = new ArrayList<>();

    public User() {
    }

    public User(String name, String email, String password, String nationality, int limitRoom) {

        this.name = name;
        this.email = email;
        this.password = password;
        this.nationality = nationality;
        this.limitRoom = limitRoom;

    }

    @PrePersist()
    public void prePersist() {

        if (this.registrationDate == null) {

            this.registrationDate = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

        }

    }

    @Override
    public int hashCode() {
        // A chave primária (ID) é a identidade do objeto
        if (id == 0) { // Se ainda não foi persistido
            return Objects.hash(email); // Um fallback decente
        }
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User other = (User) obj;
        // Compara apenas pelo ID. Se ambos forem 0, compara por um campo único (ex: email)
        if (id == 0 && other.id == 0) {
            return Objects.equals(email, other.email);
        }
        return id == other.id;
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

    public int getLimitRoom() {

        return limitRoom;

    }

    public void setLimitRoom(int limitRoom) {

        this.limitRoom = limitRoom;

    }

    public List<Room> getRooms()  {

        return rooms;

    }

    public void setRooms(List<Room> rooms) {

        this.rooms = rooms;

    }

}
