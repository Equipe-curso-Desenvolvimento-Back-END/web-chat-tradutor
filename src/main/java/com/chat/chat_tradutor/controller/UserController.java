package com.chat.chat_tradutor.controller;

import com.chat.chat_tradutor.service.UserService;

import com.chat.chat_tradutor.model.User;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.validation.BindingResult;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;

// para teste

import com.chat.chat_tradutor.common.DevConstants;

@Controller
public class UserController {

    private UserService service;

    public UserController(UserService service) {

        DevConstants.devkit = DevConstants.ON;

        this.service = service;

    }

    @PostMapping("/register")
    public String createUser(@Valid User user, BindingResult result) {

        if(result.hasErrors()) {

            // temp retornar a main index/home
            return "redirect:/";

        }

        // For testing
        if (DevConstants.devkit == DevConstants.ON) {


            System.out.println("Nome: "+user.getName());
            System.out.println("email: "+user.getEmail());
            System.out.println("Senha: "+user.getPassword());
            System.out.println("Nacionalidade: "+user.getNationality());

        }

        // temp
        return "redirect:/login";

    }

}

