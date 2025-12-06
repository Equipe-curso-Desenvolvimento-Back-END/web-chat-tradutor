package com.chat.chat_tradutor.service;

import com.chat.chat_tradutor.repository.UserRepository;
import com.chat.chat_tradutor.model.User;

import org.springframework.stereotype.Service;

import com.chat.chat_tradutor.common.UserConstants;

import java.util.Optional;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {

        this.repo = repo;

    }

    public boolean validName(String name) {

        if (name.length() > UserConstants.MAX_SIZE_NAME || name.length() <= 0) {

            throw new RuntimeException("Nome fora das normas");

        }

        return true;

    }

    public boolean validEmail(String email) {

        if (email.length() > UserConstants.MAX_SIZE_EMAIL || email.length() <= 0) {

            throw new RuntimeException("Email fora das normas");

        }

        String[] letters = email.split("");

        int cont = -1;

        for (String let : letters) {

            char a = let.charAt(0);

            if (a == UserConstants.ESPECIAL_CHAR_EMAIL) {cont++;}

        }

        if (cont != 0) {

            return false;

        }

        return true;

    }

    public boolean validPassword(String password) {

        Pattern p = Pattern.compile(UserConstants.RENGEX);
        Matcher m = p.matcher(password);

        if (!(m.find())) {

            throw new RuntimeException("Senha fora dos padroes");

        }

        return m.matches();

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
