package com.jaehong.controller;

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

import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String login() {
        return "/login";
    }

    @PostMapping
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> map) {
        String id = map.get("id");
        String pwd = map.get("pwd");

        User user = userRepository.findById(id);

        if (user == null) {
            return null;
        } else if (!user.getPwd().equals(pwd)) {
            return null;
        }
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
