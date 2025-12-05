package com.chat.chat_tradutor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // Direcionando metodos para desenho dos arquivos .html

    @GetMapping("/login")
    public String login() {

        return "login";

    }

    @GetMapping("/cadastro")
    public String cadastro() {

        return "cadastro";

    }

}
