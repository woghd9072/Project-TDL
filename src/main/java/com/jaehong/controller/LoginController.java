package com.jaehong.controller;

import com.jaehong.repository.UserRepository;
import com.jaehong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/")
    public String loginSuccess() {
        return "redirect:/tdl/list";
    }
}
