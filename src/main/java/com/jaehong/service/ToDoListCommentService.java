package com.jaehong.service;

import com.jaehong.domain.CommentVO;
import com.jaehong.domain.ToDoList;
import com.jaehong.domain.ToDoListComment;
import com.jaehong.repository.ToDoListCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ToDoListCommentService {

    @Autowired
    ToDoListCommentRepository toDoListCommentRepository;

    public CommentVO commentService(ToDoListComment toDoListComment, ToDoList currentToDoList) {
        toDoListComment.setComment(toDoListComment.getComment());
        toDoListComment.setCreatedDate(LocalDateTime.now());
        toDoListComment.setUpdatedDate(null);
        currentToDoList.add(toDoListComment);
        toDoListCommentRepository.save(toDoListComment);

        CommentVO commentVO = new CommentVO();
        commentVO.setIdx(toDoListComment.getIdx());
        commentVO.setComment(toDoListComment.getComment());
        commentVO.setCreatedDate(LocalDateTime.now());
        commentVO.setUpdatedDate(null);
        return commentVO;
    }

    public void deleteService(Integer idx) {
        toDoListCommentRepository.deleteById(idx);
    }

    public void updateService(Integer idx, String comment) {
        ToDoListComment toDoListComment = toDoListCommentRepository.getOne(idx);
        toDoListComment.update(comment);
        toDoListCommentRepository.save(toDoListComment);
    }
}
