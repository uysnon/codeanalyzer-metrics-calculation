package com.uysnon.codeanalyzer.auth.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorsController {
    @GetMapping("/error/goToLogin")
    public String goToLogin() {
        return "go_to_login";
    }
}
