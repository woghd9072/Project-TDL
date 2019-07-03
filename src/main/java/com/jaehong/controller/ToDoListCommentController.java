package com.jaehong.controller;

import com.jaehong.domain.CommentVO;
import com.jaehong.service.ToDoListCommentService;
import com.jaehong.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
@RequestMapping("/comment")
public class ToDoListCommentController {

    @Autowired
    ToDoListCommentService toDoListCommentService;

    @Autowired
    ToDoListService toDoListService;

    @PostMapping
    public ResponseEntity<?> postComment(@RequestBody Map<String, String> map) {
        CommentVO commentVO = toDoListCommentService.commentService(map);
        return new ResponseEntity<CommentVO>(commentVO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer idx) {
        toDoListCommentService.deleteService(idx);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> updateComment(@PathVariable("idx") Integer idx, @RequestBody String comment) {
        toDoListCommentService.updateService(idx, comment);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
