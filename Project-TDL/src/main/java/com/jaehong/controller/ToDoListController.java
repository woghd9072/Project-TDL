package com.jaehong.controller;

import com.jaehong.domain.ToDoList;
import com.jaehong.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tdl")
public class ToDoListController {

    @Autowired
    ToDoListService toDoListService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("tdlList", toDoListService.findList());
        return "/tdl/list";
    }

    @PostMapping
    public ResponseEntity<?> posttdl(@RequestBody ToDoList toDoList) {
        toDoListService.postService(toDoList);
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
