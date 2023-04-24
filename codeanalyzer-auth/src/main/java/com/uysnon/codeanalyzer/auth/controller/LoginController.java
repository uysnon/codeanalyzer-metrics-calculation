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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Controller
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    // defaultValue = base64encode("/hello/user")
    @GetMapping("/login")
    public String login(@RequestParam(name="page", defaultValue="L2hlbGxvL3VzZXI=")String page,
                        Model model) {
        model.addAttribute("page", page);
        return "login";
    }

    @PostMapping("/login/try")
    public String loginTry(@RequestParam("passwordForm") String password,
                           @RequestParam("loginForm") String login,
                           @RequestParam("page") String page,
                           Model model,
                           HttpServletResponse response) throws IOException {
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
        byte[] decodedBytesOfPage = Base64.getDecoder().decode(page);
        String pageDecoded = new String(decodedBytesOfPage, StandardCharsets.UTF_8);
        model.addAttribute("page", pageDecoded);

        return "redirect";
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String onBadCrenetialsException() {
        return "redirect:/login";
    }
}
