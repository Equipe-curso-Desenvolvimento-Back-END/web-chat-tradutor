package com.chat.chat_tradutor.service;

import com.chat.chat_tradutor.repository.UserRepository;
import com.chat.chat_tradutor.model.User;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {

        this.repo = repo;

    }

    // posteriormente
    public User saveUser(User user) {

        User local = user;

        // verifica se todo campo e falso de acordo com RN
        if (!(repo.existsByName(local.getName())) || !(repo.existsByEmail(local.getEmail())) || !(repo.existsByPassword(local.getPassword()))) {

            return null;

        }

        return local;

    }

    // add outros cruds e tratamentos

}
