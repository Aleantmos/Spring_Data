package com.example.workshop.controllers;

import com.example.workshop.model.users.LoginDTO;
import com.example.workshop.model.users.User;
import com.example.workshop.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class UserController {

    private UserService userService;

    @GetMapping("/users/login")
    public String login() {
        return "/user/login";
    }

    @PostMapping("/users/login")
    public String doLogin(LoginDTO loginDTO) {
        Optional<User> user = userService.login(loginDTO);
        if (user.isPresent()) {
            return "redirect:/home";
        }
        return "/user/login";
    }
}
