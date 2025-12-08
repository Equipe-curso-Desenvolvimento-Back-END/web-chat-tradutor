package com.chat.chat_tradutor.controller;

import com.chat.chat_tradutor.service.RoomService;
import com.chat.chat_tradutor.service.UserService;

import com.chat.chat_tradutor.repository.RoomRepository;
import com.chat.chat_tradutor.repository.UserRepository;

import com.chat.chat_tradutor.model.User;
import com.chat.chat_tradutor.model.Room;

//Nao sei se vai usar
//import com.chat.chat_tradutor.common.UserConstants;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.validation.BindingResult;

import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/chat")
public class ChatController {

    private final RoomService roomService;
    private final UserService userService;

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    public ChatController(RoomService roomService,
            UserService userService,
            RoomRepository room, UserRepository user) {

        this.roomService = roomService;
        this.userService = userService;

        this.roomRepository = room;
        this.userRepository = user;

    }

    @GetMapping("/{roomId}")
    public String enterChatRoom(@PathVariable Long roomId,
            Model model, HttpSession session) {

        // usando o meu proprio list
        // armazenar seguraca com o id do usuario

        Room room = roomService.findById(roomId);

        if (room == null) {

            throw new RuntimeException("Sala não existe!");

        }

        // temporario retorno com http

        model.addAttribute("room",room);

        // creatorId
        Long currentUserId = (Long) session.getAttribute("userId");

        // add user na room Mais tarde

        User local = userService.readUser(currentUserId);

        if (local == null) {

            throw new RuntimeException("Sala não existe!");

        }

        /// Condicao para evitar duplicata!
        if (room.getCreatorId() == local.getId()) {

            return "chat/room";

        }

        // Condicao para evitar duplicata usuario!
        // Usuario utliza estrutura de dado SetHash
        Room roomVerify = roomService.saveUser(room,local);

        if (roomVerify != null) {

            User userVerify = userService.saveRoom(local,roomVerify);

            roomService.updateRoom(roomVerify);

            // possibilidade de salvar o usuario para sincronziacao completao
            // somente se for necessario
            // userService.updateUser(userVerify);

        }

        //boolean wasAdded = room.getUsers().add(local);

        //if (wasAdded) {


          //  local.getRooms().add(room);

         //   roomService.updateRoom(room);

        //}

        return "chat/room";

    }

}
