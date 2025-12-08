package com.chat.chat_tradutor.repository;

import com.chat.chat_tradutor.model.User;

// se for preciso add JPA para mudancas na implementacao dos metodosstereotype
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    // associacao bidirecional
    // possivel erro no tipo
    User findByIdAndRoomsId(Long userId, Long  roomId);

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

    // bidirencional

    boolean existsByIdAndRoomsId(Long userId, Long roomId);
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END " +
       "FROM User u JOIN u.rooms r " +
       "WHERE u.id = :userId AND r.id = :roomId")
    boolean isUserMemberOfRoom(@Param("userId") Long userId, @Param("roomId") Long roomId);

}

