package com.jaehong.controller;

import com.jaehong.domain.User;
import com.jaehong.domain.UserDto;
import com.jaehong.service.RegisterService;
import com.jaehong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    UserService userService;

    @Autowired
    RegisterService registerService;

    @GetMapping
    public String register() {
        return "/register";
    }

    @PostMapping
    public ResponseEntity<?> postUser(@Valid @RequestBody UserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST);
        }
        userService.save(userDto.toEntity());
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @PostMapping("/confirm/id")
    public ResponseEntity<?> checkId(@RequestBody Map<String, String> map) {
        return registerService.confirmId(map.get("id")) ? new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST) : new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @PostMapping("/confirm/email")
    public ResponseEntity<?> checkEmail(@RequestBody Map<String, String> map) {
        return registerService.confirmEmail(map.get("email")) ? new ResponseEntity<>("{}", HttpStatus.BAD_REQUEST) : new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
