package com.chat.chat_tradutor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;

// Vai permitir que exita rooms/create rooms/room/{id} etc...
@Controller
@RequestMapping("/rooms")
public class RoomController {

    // sempre implementar o security nas novas paginas fixas
    @GetMapping("")
    public String roomsHome(HttpSession session) {

        if (session.getAttribute("user") == null) {

            // passivo de falha
            return "redirect:/login";

        }

        return "rooms/index";

    }

    @GetMapping("/create")
    public String createPage(HttpSession session) {

        if (session.getAttribute("user") == null) {

            return "redirect:/login";

        }

        return "rooms/create";
    }

    // Finalize security
    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/login";

    }

    // Tambem adicionar o security
    // Redirecionar novamente a sala apos a criacao

    @PostMapping("/create")
    public String createRoom() {

        // futuramente ira ser a sala com n valor id
        return "redirect:/rooms";

    }

}

