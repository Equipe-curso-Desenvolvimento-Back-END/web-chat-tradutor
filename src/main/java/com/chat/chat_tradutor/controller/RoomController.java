package com.chat.chat_tradutor.controller;

import com.chat.chat_tradutor.service.RoomService;
import com.chat.chat_tradutor.service.UserService;

import com.chat.chat_tradutor.model.Room;
import com.chat.chat_tradutor.model.User;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.validation.BindingResult;

import jakarta.servlet.http.HttpSession;

// Vai permitir que exita rooms/create rooms/room/{id} etc...
@Controller
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService service;
    private final UserService userService;

    public RoomController(RoomService service, UserService userService) {

        this.service = service;
        this.userService = userService;

    }

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

    @GetMapping("/remove")
    public String createPageRemove(HttpSession session) {

        if (session.getAttribute("user") == null) {

            return "redirect:/login";

        }

        return "rooms/remove";

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
    public String createRoom(@ModelAttribute Room room, BindingResult result,HttpSession session) {

        if (result.hasErrors()) {

            return "redirect:rooms/create";

        }

        if (room.getName().length() == 0 || room.getDescription().length() == 0) {

            throw new RuntimeException("Campos vazios!");

        }

        // cadastrando o usuario atual como dono da sala

        Long creatorId = (Long) session.getAttribute("userId");

        if (creatorId == null) {
            throw new RuntimeException("Usuario nao autenticado");

        }

        room.setCreatorId(creatorId);

        // cadastrando como um participante


        // Salvando no DB a sala, com o dono


        // se atentar com essa parte do codigo, importante
        // pois para fazer alteracoes de negocio deve-se
        // sabe o que esta ocorrento

        // Nessa linha nos usamos o metodo de servico da sala
        // para salvar a sala com o seu respectivo dono representado
        // pelo seu id = creatorId
        // E retornado o objeto da sala de cadastro com o primeiro dono
        // e usuario dentro da sala, assim sendo cadastrado no banco

        Room saved = service.saveRoom(service.saveUser(room,userService.readUser(creatorId)));

        if (saved == null) {

            throw new RuntimeException("Nome da sala ja existe!");

        }

        System.out.println("Repositorio: "+saved.getName()+" Cadastrado");
        System.out.println("Dono: "+userService.readUser(creatorId).getName()+" !!!");

        // futuramente ira ser a sala com n valor id
        return "redirect:/rooms";

    }

    @PostMapping("/remove")
    public String deleteRoom(
            @RequestParam("name") String name,
            @RequestParam(name = "isConfirmed", required = false) boolean checkbox,HttpSession session) {

            Room room = service.findByName(name);

            if (room == null) {

                throw new RuntimeException("Nome nao existe!");

            }

            if (!(checkbox)) {

                //return "redirect/rooms?erro=confirmation_requeried";
                throw new RuntimeException("checkbox nao marcado");

            }

            Long creatorId = (Long) session.getAttribute("userId");

            if (creatorId == null) {

                throw new RuntimeException("Usuario nao autenticado");

            }

            if (!(creatorId.equals(room.getCreatorId()))) {

                throw new RuntimeException("Impossivel apagar voce nao e o dono da sala");

            }

            if (service.deleteRoom(room.getId())) {

                System.out.println("A sala"+room.getName()+" foi removida!");

                return "redirect:/rooms/remove";
            }

            System.out.println("Nao foi possivel remover sua sala");

            // possivel fultura mensagem de confirmacao
            // e manter o usuario ainda no delete
            // futuramente

            return "redirect:/rooms"; // /rooms/delete

    }

}

