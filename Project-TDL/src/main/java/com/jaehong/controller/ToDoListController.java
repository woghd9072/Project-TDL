package com.jaehong.controller;

import com.jaehong.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String posttdl(@RequestBody String description) {
        toDoListService.postService(description);
        return "redirect:/tdl/list";
    }
}
