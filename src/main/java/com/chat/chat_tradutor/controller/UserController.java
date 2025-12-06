package com.chat.chat_tradutor.controller;

import com.chat.chat_tradutor.service.UserService;

import com.chat.chat_tradutor.model.User;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.validation.BindingResult;

//import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.stereotype.Controller;

// para teste

import com.chat.chat_tradutor.config.DevConstants;

@Controller
public class UserController {

    private UserService service;

    public UserController(UserService service) {

        DevConstants.devkit = DevConstants.ON;

        this.service = service;

    }

    @PostMapping("/register")
    public String createUser(@ModelAttribute User user, BindingResult result) {

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


        // try catch temp para testes
        try{

            User teste = service.saveUser(user);
            //teste.setName("0101010001001");
            //service.patchUser(teste.getId(),teste);

        }catch(Exception e) {

            return "redirect:/register";

        }

        return "redirect:/login";

    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, BindingResult result) {

        if(result.hasErrors()) {

            // temp retornar a main index/home
            return "redirect:/register";

        }

        try{

            User local = service.loginUser(user.getId(),user);
            System.out.println("Conta de"+local+" logada!");

        }catch(Exception e) {

            return "redirect:/login";

        }

        // temp ira para sala room
        return "redrect:/";

    }

}

