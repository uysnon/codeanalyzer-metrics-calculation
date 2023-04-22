package com.uysnon.codeanalyzer.auth.controller;

import com.uysnon.codeanalyzer.auth.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login/try")
    public String loginTry(@RequestParam("passwordForm") String password,
                           @RequestParam("loginForm") String login,
                           HttpServletResponse response) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            login,
                            password)
            );
        } catch (
                BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password", e);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(login);
        String token = jwtUtil.generateToken(userDetails);
        Cookie cookie = new Cookie("codeanalyzer-authorization", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return "login";
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String onBadCrenetialsException() {
        return "redirect:/login";
    }

}
