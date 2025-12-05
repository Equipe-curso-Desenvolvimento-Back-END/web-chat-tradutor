package com.chat.chat_tradutor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Redirecionando por natureza ao /home

    @GetMapping("/")
    public String root() {

        return "redirect:/home"; // home.html

    }

    // Direcionando metodos para desenho dos arquivos .html

    @GetMapping("/home")
    public String home() {

        return "home";

    }

    @GetMapping("/login")
    public String login() {

        return "login";

    }

    @GetMapping("/cadastro")
    public String cadastro() {

        return "cadastro";

    }

}
