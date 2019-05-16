package com.jaehong.controller;

import com.jaehong.repository.UserRepository;
import com.jaehong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping
    public String login() {
        return "/login";
    }

    @PostMapping
    public String loginSuccess() {
        return "redirect:/tdl/list";
    }
}
