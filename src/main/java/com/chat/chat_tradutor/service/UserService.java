package com.chat.chat_tradutor.service;

import com.chat.chat_tradutor.repository.UserRepository;
import com.chat.chat_tradutor.model.User;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {

        this.repo = repo;

    }

    // posteriormente
    public User saveUser(User user) throws RuntimeException {

        // verifica se todo campo e falso de acordo com RN

        if (repo.existsByName(user.getName()) || repo.existsByEmail(user.getEmail()) || repo.existsByPassword(user.getPassword())) {

            throw new RuntimeException("Usuario ja existe!");

        }

        repo.save(user);

        return user;

    }

    // somente User por contado login ser metodo POST
    public User loginUser(User user) {

        if (repo.existsByEmail(user.getEmail()) && repo.existsByPassword(user.getPassword())) {

            return user;

        }

        throw new RuntimeException("Valor incorretos!");

    }

    // testing to read
    // ainda nao testando
    public User readUser(long id, User user) {

        return repo.findById(id).orElse(null);

    }

    // teste de atualizacao all
    public User patchUser(long id, User user) {

        User local = repo.findById(id).orElse(null);

        if (local == null) {

            return null;

        }

        if (user.getName() != null) local.setName(user.getName());
        if (user.getEmail() != null) local.setEmail(user.getEmail());
        if (user.getPassword() != null) local.setPassword(user.getPassword());
        if (user.getNationality() != null) local.setNationality(user.getNationality());

        repo.save(local);

        return local;

    }

    public boolean removeUser(long id) {

        User user = repo.findById(id).orElse(null);

        if (user == null) {

            return false;

        }

        repo.deleteById(user.getId());

        return true;

    }

    // add outros cruds e tratamentos

}
