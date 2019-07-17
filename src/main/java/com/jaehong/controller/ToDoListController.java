package com.jaehong.controller;

import com.jaehong.domain.ToDoList;
import com.jaehong.domain.User;
import com.jaehong.file.StorageService;
import com.jaehong.service.ToDoListService;
import com.jaehong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/tdl")
public class ToDoListController {

    @Autowired
    ToDoListService toDoListService;

    @Autowired
    UserService userService;

    private User currentUser;

    @GetMapping("/list")
    public String list(Model model) {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        currentUser = userService.findUser(user.getUsername());
        model.addAttribute("tdlList", toDoListService.findList(currentUser.getIdx()));
        return "/tdl/list";
    }

    @PostMapping
    public ResponseEntity<?> posttdl(@RequestBody ToDoList toDoList) {
        toDoListService.postService(toDoList, currentUser);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @DeleteMapping("/{idx}")
    public  ResponseEntity<?> deletetdl(@PathVariable("idx") Integer idx) {
        toDoListService.deleteService(idx);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> updatetdl(@PathVariable("idx") Integer idx, @RequestBody String description) {
        toDoListService.updateService(idx, description);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @PutMapping("/complete/{idx}")
    public ResponseEntity<?> completetdl(@PathVariable("idx") Integer idx) {
        toDoListService.completeService(idx);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
