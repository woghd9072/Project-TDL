package com.jaehong.controller;

import com.jaehong.domain.ToDoList;
import com.jaehong.domain.User;
import com.jaehong.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/tdl")
public class ToDoListController {

    @Autowired
    ToDoListService toDoListService;

    private User currentUser;

    @GetMapping("/list")
    public String list(Model model) {
        if(currentUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("tdlList", toDoListService.findList(currentUser.getIdx()));
        return "/tdl/list";
    }

    @GetMapping("/list/logout")
    public String logout() {
        currentUser = null;
        return "redirect:/tdl/list";
    }

    @PostMapping("/user")
    public ResponseEntity<?> current(@RequestBody Map<String, String> map) {
        currentUser = toDoListService.findUser(map.get("id"));
        return new ResponseEntity<>("{}", HttpStatus.OK);
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
