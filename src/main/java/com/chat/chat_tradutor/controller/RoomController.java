package com.chat.chat_tradutor.controller;

import com.chat.chat_tradutor.repository.MessageRepository;

import com.chat.chat_tradutor.service.RoomService;
import com.chat.chat_tradutor.service.UserService;

import com.chat.chat_tradutor.model.Room;
import com.chat.chat_tradutor.model.User;

import com.chat.chat_tradutor.common.UserConstants;

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

// Vai permitir que exita rooms/create rooms/room/{id} etc...
@Controller
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService service;
    private final UserService userService;

    private final MessageRepository messageRepository;

    public RoomController(RoomService service,
            UserService userService,
            MessageRepository messageRepository) {

        this.service = service;
        this.userService = userService;

        this.messageRepository = messageRepository;

    }

    @GetMapping
    public String listRooms(Model model,
            HttpSession session) {

        if (session.getAttribute("user") == null) {

            return "redirect:/login";

        }

        List<Room> rooms = service.findAll();

        model.addAttribute("roomsList",rooms);

        return "rooms/index";

    }

    // formato resumido sem rooms/room/{roomId}
    @GetMapping("/{roomId}")
    public String accessRoom(@PathVariable Long roomId, Model model, HttpSession session) {

        Room room = service.findById(roomId);

        if (room == null) {

            throw new RuntimeException("Sala não existe!");

        }

         model.addAttribute("room",room);

        // creatorId
        Long currentUserId = (Long) session.getAttribute("userId");

        // add user na room Mais tarde

        User local = userService.readUser(currentUserId);

        /// Condicao para evitar duplicata!
        if (room.getCreatorId() == local.getId()) {

            return "rooms/room";

        }

        Room roomVerify = service.saveUser(room,local);

        if (roomVerify != null) {

            User userVerify = userService.saveRoom(local,roomVerify);

            service.updateRoom(roomVerify);

            // possibilidade de salvar o usuario para sincronziacao completao
            // somente se for necessario
            // userService.updateUser(userVerify);

        }

        var messageHistory = messageRepository.findByRoomIdOrderByTimestampAsc(roomId);

        model.addAttribute("messageHistory",messageHistory);

        return "rooms/room";

    }

    @GetMapping("/myaccount")
    public String listMyRooms(HttpSession session,
            Model model, RedirectAttributes flash) {

        if (session.getAttribute("user") == null) {

            return "redirect:/login";

        }

        Long creatorId = (Long) session.getAttribute("userId");

        if (creatorId == null) {

            flash.addFlashAttribute("myAccountError", "Impossivel acessar, Usuario não foi autenticado!");

            return "rooms/login";

        }

        List<Room> myRooms = service.findAllByCreatorId(creatorId);
        User local = userService.readUser(creatorId);

        // cadastrado a lista no model da raiz /myaccount
        model.addAttribute("myRoomsList",myRooms);
        // cadastrado a lista de
        model.addAttribute("myClientInfo",local);

        // codigos seguintes de cremento de variavel
        // na instancia de modal ja existente

        if (myRooms.isEmpty()) {

            model.addAttribute("myAccountEmptyError", "Você ainda não cadastrou nem uma sala!");

            return "rooms/myaccount";

        }

        model.addAttribute("myAccountConfirm", "Resgate das suas salas foi um sucesso!");

        return "rooms/myaccount";

    }

    // Atualizar room

    @GetMapping("/update")
    public String createPageUpdate(HttpSession session) {

        if (session.getAttribute("user") == null) {

            return "redirect:/login";

        }

        return "rooms/update";

    }

    @GetMapping("/configaccount")
    public String createPageMyAccountUpdate(HttpSession session) {

        if (session.getAttribute("user") == null) {

            return "redirect:/login";

        }

        return "rooms/configaccount";

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

    @PostMapping("/remove/{roomId}")
    public String deleteRoom(@PathVariable("roomId") Long roomId, RedirectAttributes flash) {

        service.deleteRoom(roomId);

        flash.addFlashAttribute("acertMessage", "Sala removida com sucesso!");

        // Para atualizar o HTTP forcar get
        return "redirect:/rooms";


    }

    // Tambem adicionar o security
    // Redirecionar novamente a sala apos a criacao

    @PostMapping("/create")
    public String createRoom(@ModelAttribute Room room,
            BindingResult result,
            HttpSession session,
            RedirectAttributes flash) {

        if (result.hasErrors()) {

            flash.addFlashAttribute("createRoomError", "Ocorreu um erro inesperado!");

            return "redirect:rooms/create";

        }

        if (room.getName().length() == 0 || room.getDescription().length() == 0) {

            flash.addFlashAttribute("createRoomError", "Campos de nome ou descrição incorretos ou vazios!");

            return "redirect:/rooms/create";

        }

        Long creatorId = (Long) session.getAttribute("userId");

        if (creatorId == null) {

            flash.addFlashAttribute("createRoomError", "Impossivel acessar, Usuario não foi autenticado!");

            return "redirect:/rooms/create";

        }

        room.setCreatorId(creatorId);

        // setando nome e email na room

        User local = userService.readUser(creatorId);

        room.setCreatorName(local.getName());
        room.setCreatorEmail(local.getEmail());
        room.setCreatorNationality(local.getNationality());

        local.setLimitRoom(local.getLimitRoom()+1);

        // verificando se passou o limit de salas por usuario
        if (local.getLimitRoom() > UserConstants.MAX_ROOMS) {

            flash.addFlashAttribute("createRoomError",String.format("Você não pode criar mais salas, já está no seu máximo de %d",UserConstants.MAX_ROOMS));

            return "redirect:/rooms/create";

        }

        // Nessa linha nos usamos o metodo de servico da sala
        // para salvar a sala com o seu respectivo dono representado
        // pelo seu id = creatorId
        // E retornado o objeto da sala de cadastro com o primeiro dono
        // e usuario dentro da sala, assim sendo cadastrado no banco

        Room saved = service.saveRoom(service.saveUser(room,local));

        if (saved == null) {

            flash.addFlashAttribute("createRoomError", "Nome de sala já existetente!");

            return "redirect:/rooms/create";

        }

        flash.addFlashAttribute("createRoomConfirm", String.format("Criação da sala \"%s\", foi um sucesso! Volte para o menu global de salas ou para seu pervil em \"My Account\" para acessar!",saved.getName()));

        return "redirect:/rooms/create";

    }

    @PostMapping("/remove")
    public String deleteRoom(
            @RequestParam("name") String name,
            @RequestParam(name = "isConfirmed", required = false) boolean checkbox,
            HttpSession session,
            RedirectAttributes flash) {

            Room room = service.findByName(name);

            if (room == null) {

                flash.addFlashAttribute("deleteRoomError", "Nome da sala não é existente!");

                return "redirect:/rooms/remove";

            }

            if (!(checkbox)) {

                flash.addFlashAttribute("deleteRoomError", "Checkbox deve ser obrigatoriamente marcada!");

                return "redirect:/rooms/remove";

            }

            Long creatorId = (Long) session.getAttribute("userId");

            System.out.println("Creator: "+creatorId);

            if (creatorId == null) {

                flash.addFlashAttribute("deleteRoomError", "Impossivel acessar, Usuario não foi autenticado!");

                return "redirect:/rooms/remove";

            }

            User local = userService.readUser(creatorId);

            if (!(creatorId.equals(room.getCreatorId()))) {


                flash.addFlashAttribute("deleteRoomError", "Não é possivel apagar a sala, você não é o dono!");

                return "redirect:/rooms/remove";

            }

            // Caso for necessario e possivel adicionar
            // um campo de redundancia para verificar se o usuario
            // possui alguma sala, evitando valores negativos. Mas claro o Id ja cobra essa protecao

            if (local.getLimitRoom() > 0) {

                local.setLimitRoom(local.getLimitRoom()-1);

            }

            // Remover sala

            if (!(service.deleteRoom(room.getId()))) {

                flash.addFlashAttribute("deleteRoomError", String.format("Não foi possivel remove a sala \"%d\"",room.getName()));

                return "redirect:/rooms/remove";

            }

            flash.addFlashAttribute("deleteRoomError", String.format("Sala \"%s\" foi removida com sucesso!",room.getName()));

            // Por base usuario se mantem em delete
            // Somente um redirect na funcao deve conter para esse formato

            return "redirect:/rooms/remove";

    }

    @PostMapping("/update")
    public String updateRoom(@RequestParam("oldname") String oldName,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            HttpSession session,
            RedirectAttributes flash) {

        if (oldName.length() == 0) {

            flash.addFlashAttribute("updateRoomError","Passe o nome da sala que deseja alterar e seus novos parametros");

            return "redirect:/rooms/update";

        }

        // Verificar se existe o room

        Room room = service.findByName(oldName);

        if (room == null) {

            flash.addFlashAttribute("updateRoomError","Sala não encontrada!");

        }

        Long creatorId = (Long) session.getAttribute("userId");

        if (!(creatorId.equals(room.getCreatorId()))) {

            flash.addFlashAttribute("updateRoomError","Não é possivel atualizar a sala, você não é o dono!");

            return "redirect:/rooms/update";

        }

        // formato temporario

        if (name.length() == 0 && description.length() == 0) {

            flash.addFlashAttribute("updateRoomError","Campos de atualização não foram passados. Nada foi alterado!");

            return "redirect:/rooms/update";

        }

        if (room.getName().equals(name) && room.getDescription().equals(description)) {

            flash.addFlashAttribute("updateRoomAlert","Em campo \"nome\" e \"description\" estão iguais aos originais!");

            return "redirect:/rooms/update";

        }

        if (name.length() == 0) {

            room.setDescription(description);

        }else if (description.length() == 0) {

            room.setName(name);

        }else {

            room.setName(name);
            room.setDescription(description);

        }

        service.updateRoom(room);


        flash.addFlashAttribute("updateRoomConfirm","Atualizações foram um sucesso!");
        return "redirect:/rooms/update";

    }

    @PostMapping("/configaccount")
    public String updateMyAccount(@RequestParam("name") String name,
            @RequestParam("password") String password,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("nationality") String nationality,
            HttpSession session,
            RedirectAttributes flash) {

                Long creatorId = (Long) session.getAttribute("userId");

                User local = userService.readUser(creatorId);

                if (local == null) {

                    flash.addFlashAttribute("updateAccountError","Conta não registrada!");

                    return "redirect:/rooms/configaccount";

                }

                if (!(local.getPassword().equals(password)) && newPassword.length() > 0) {

                    flash.addFlashAttribute("updateAccountAlert","Senha passada da conta não corresponde a senha real!");

                    return "redirect:/rooms/configaccount";

                }

                if (name.length() == 0 && password.length() == 0 && newPassword.length() == 0) {

                    flash.addFlashAttribute("updateAccountAlert","Campos vazios!");

                }


                if (password.length() == 0 && password.length() < newPassword.length() || newPassword.length() == 0 && newPassword.length() < password.length() ) {


                    flash.addFlashAttribute("updateAccountError","Deve-se ter preenchido ambos campos de \"senha\" para alteracao");

                    return "redirect:/rooms/configaccount";

                }

                if (name.equals(local.getName())) {


                    flash.addFlashAttribute("updateAccountAlert","O campo \"nome\" está igual a antigo");

                    return "redirect:/rooms/configaccount";

                }

                if (name.length() == 0) {

                    if (newPassword.length() <= 0) {

                        // testar
                        local.setPassword(local.getPassword());

                    }else {

                        local.setPassword(newPassword);

                    }

                }else if (password.length() == 0 && newPassword.length() == 0) {

                    local.setName(name);
                    local.setPassword(local.getPassword());

                }

                local.setNationality(nationality);

                List<Room> rooms = service.findAllByCreatorId(creatorId);


                // atualizando as salas com a mudanca
                if (!(rooms.isEmpty())) {

                    for (Room i : rooms) {

                        i.setCreatorName(local.getName());
                        i.setCreatorNationality(local.getNationality());

                        // Nao recomendando
                        service.updateRoom(i);

                    }


                }


                if (!(userService.validPassword(newPassword)) && newPassword.length() > 0) {

                    flash.addFlashAttribute("updateAccountConfirm","Senha não é valida!");

                    return "redirect:/rooms/configaccount";

                }

                userService.simplePatchUser(local);

                flash.addFlashAttribute("updateAccountConfirm","Alterações do usuario feitas com exito!");

                return "redirect:/rooms/configaccount";

            }

}

