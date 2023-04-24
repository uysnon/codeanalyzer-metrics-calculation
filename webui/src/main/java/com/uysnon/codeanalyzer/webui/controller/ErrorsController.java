package com.uysnon.codeanalyzer.webui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.AccessDeniedException;

@Controller
public class ErrorsController {

    @GetMapping("/error/goToLogin/{page}")
    public String goToLogin(@PathVariable("page") String page,
                            HttpServletRequest req,
                            HttpServletResponse httpServletResponse,
                            Model model) {
        model.addAttribute("page", page);
        return "go_to_login";
    }
}
