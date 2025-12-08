package com.chat.chat_tradutor.service;

import com.chat.chat_tradutor.model.Room;
import com.chat.chat_tradutor.model.ThreadChat;
import com.chat.chat_tradutor.model.User;

import com.chat.chat_tradutor.repository.RoomRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import java.lang.Iterable;

@Service
public class RoomService {

    private final RoomRepository repo;

    public RoomService(RoomRepository repo) {

        this.repo = repo;

    }

    // CRUD para Room

    public Room saveRoom(Room room) {

        if (room == null) {

            return null;

        }

        if (repo.existsByName(room.getName())) {

            return null;

        }

        return repo.save(room);

    }

    public Room updateRoom(Room room) {

        return repo.save(room);

    }

    public List<Room> findAll() {

        return repo.findAll();

    }

    // sobrescrita pode gerar erro
    public Room findById(Long id) {

        // metodo padrao do Spring  Boot
        return repo.findById(id).orElse(null);

    }
    public Room findById(long id) {

        return repo.findById(id).orElse(null);

    }

    public Room findByName(String name) {

        Optional<Room> roomOptional = repo.findByName(name);

        return roomOptional.orElse(null);

    }

    public List<Room> findAllByCreatorId(Long id) {

        return repo.findAllByCreatorId(id);

    }

    // Se atentar com o erro de nao remover
    // as tabelas associadas

    public boolean deleteRoom(long id) {

        if (!(repo.existsById(id))) {

            return false;

        }

        repo.deleteById(id);

        return true;

    }

    // CRUD para Users em ROOM

    // pode gerar erro de escrita
    public Room saveUser(Room room, User user) {

        if (repo.existsByName(user.getName())) {

            return null;

        }

        room.getUsers().add(user);

        return room;

    }

    public Room removeUser(Room room, User user) {

        if (!(repo.existsByName(user.getName()))) {

            return null;

        }

        room.getUsers().remove(user);

        return room;

    }


    // CRUD para ThreadChats

    public Room saveThread(Room room, ThreadChat thread) {

        room.getThreadChats().add(thread);

        return room;

    }

    // add removeThread

    public List<ThreadChat> getThreads(Room room) {

        return room.getThreadChats();

    }


}

