package com.jaehong.controller;

import com.jaehong.domain.User;
import com.jaehong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    UserService userService;

    @GetMapping
    public String register() {
        return "/register";
    }

    @PostMapping
    public ResponseEntity<?> postUser(@RequestBody Map<String, String> map) {
        User user = User.builder().id(map.get("id")).pwd(map.get("pwd")).email(map.get("email")).build();
        userService.save(user);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }
}
