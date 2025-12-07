package com.chat.chat_tradutor.controller;

import com.chat.chat_tradutor.service.UserService;

import com.chat.chat_tradutor.model.User;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.validation.BindingResult;

//import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.stereotype.Controller;

import jakarta.servlet.http.HttpSession;

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
            System.out.println("Data Cadastro: "+user.getRegistrationDate());

        }


        // try catch temp para testes
        try{

            service.validName(user.getName());
            service.validEmail(user.getEmail());
            service.validPassword(user.getPassword());

            User teste = service.saveUser(user);

        }catch(Exception e) {

            e.printStackTrace();

            return "redirect:/register";

        }

        return "redirect:/login";

    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, BindingResult result, HttpSession session) {

        if(result.hasErrors()) {

            // temp retornar a main index/home
            return "redirect:/register";

        }

        // temporario ate colocar tags de mensagem jtml
        try {

            User local = service.loginUser(user);

            // security method avaible
            if (local == null) {

                return "redirect:/login?error=true";

            }

            if (DevConstants.devkit == DevConstants.ON) {

                System.out.println("Identificador: "+local.getId());
                System.out.println("Data do registro: "+local.getRegistrationDate());
                System.out.println("Conta(nome): "+local.getName()+" logada!");

            }

            session.setAttribute("user",local);

        }catch(Exception e) {

            return "redirect:/login";

        }

        // salvar a sessao

        // temp ira para sala room
        return "redirect:/rooms";

    }

}

