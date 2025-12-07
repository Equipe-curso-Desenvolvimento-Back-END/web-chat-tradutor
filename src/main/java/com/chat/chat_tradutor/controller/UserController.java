package com.chat.chat_tradutor.controller;

import com.chat.chat_tradutor.service.UserService;

import com.chat.chat_tradutor.model.User;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.validation.BindingResult;

import org.springframework.stereotype.Controller;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

import com.chat.chat_tradutor.common.UserConstants;

@Controller
public class UserController {

    private UserService service;

    public UserController(UserService service) {

        this.service = service;

    }

    @PostMapping("/register")
    public String createUser(@ModelAttribute User user,
            BindingResult result,
            RedirectAttributes flash) {

        if(result.hasErrors()) {


            return "redirect:/?error=true";
        }

        if (!(service.validName(user.getName()))) {

            flash.addFlashAttribute("namedError",String.format("Siga os padrões aconselhados. Tamanho máximo de %d caracteres :0 ",UserConstants.MAX_SIZE_NAME));

            return "redirect:/register";

        }

        if (!(service.validEmail(user.getEmail()))) {

            flash.addFlashAttribute("emailError",String.format("Siga os padrões aconselhados. Sempre usar @ e máximo de %d caracteres :w ",UserConstants.MAX_SIZE_EMAIL));

            return "redirect:/register";

        }


        if (!(service.validPassword(user.getPassword()))) {

            flash.addFlashAttribute("passwordError",String.format("Siga os padrões aconselhados. Minimo de %d caracteres, pelo menos %d letra maiuscula, pelo menos %d minuscula e lista da caracteres disponiveis: & $ ! * #",8,1,1));

            return "redirect:/register";

        }

        User teste = service.saveUser(user);

        return "redirect:/login";

    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user,
            BindingResult result,
            HttpSession session,
            RedirectAttributes flash) {

        if(result.hasErrors()) {

            return "redirect:/?error=true";

        }

        User local = service.loginUser(user);

        if (local == null) {

            flash.addFlashAttribute("loginError","Email ou Senha incorretos.");

            return "redirect:/login?error=true";

        }

        session.setAttribute("user",local);
        session.setAttribute("userId",local.getId());

        return "redirect:/rooms";

    }

}

