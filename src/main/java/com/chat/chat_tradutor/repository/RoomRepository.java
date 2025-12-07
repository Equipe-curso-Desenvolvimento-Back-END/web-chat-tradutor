package com.chat.chat_tradutor.repository;

import com.chat.chat_tradutor.model.Room;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    // metodo de listagem por nome/futura barra de pesquisa

    Optional<Room> findByName(String name);

    List<Room> findByDescription(String description);

    List<Room> findByNameAndDescription(String name, String description);

    // lista do criador

    boolean existsById(long id);
    boolean existsByName(String name);
    boolean existsByDescription(String description);

}
