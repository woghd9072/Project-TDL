package com.jaehong.controller;

import com.jaehong.domain.ToDoList;
import com.jaehong.domain.User;
import com.jaehong.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String register() {
        return "/register";
    }

    @PostMapping
    public ResponseEntity<?> postUser(@RequestBody User user) {
        String input = user.getId() + user.getPwd() + user.getEmail();
        if (input.length() == 0) {
            return null;
        }
        if (user.getId().equals(userRepository.findAll())) {
            return null;
        }
        userRepository.save(user);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
