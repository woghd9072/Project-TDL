package com.jaehong.controller;

import com.jaehong.domain.CommentVO;
import com.jaehong.domain.ToDoList;
import com.jaehong.domain.ToDoListComment;
import com.jaehong.service.ToDoListCommentService;
import com.jaehong.service.ToDoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/comment")
public class ToDoListCommentController {

    @Autowired
    ToDoListCommentService toDoListCommentService;

    @Autowired
    ToDoListService toDoListService;

    private ToDoList currentToDoList;

    @PostMapping("/add")
    public ResponseEntity<?> comment(@RequestBody ToDoList toDoList) {
        currentToDoList = toDoListService.findTdl(toDoList.getIdx());
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> postComment(@RequestBody ToDoListComment toDoListComment) {
        CommentVO commentVO = toDoListCommentService.commentService(toDoListComment, currentToDoList);
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
