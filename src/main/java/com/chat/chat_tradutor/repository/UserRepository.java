package com.chat.chat_tradutor.repository;

import com.chat.chat_tradutor.model.User;

// se for preciso add JPA para mudancas na implementacao dos metodosstereotype
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    // metodos crud base

    // to logical find

    Optional<User> findById(long id);

    //User findById(long id);

    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);
    Optional<User> findByPassword(String password);
    Optional<User> findByNationality(String nationality);

    Optional<User> findByEmailAndPassword(String email, String password);

    boolean existsById(long id);
    boolean existsByEmail(String email);
    boolean existsByName(String name);
    boolean existsByPassword(String password);

    void deleteById(long id);

}

