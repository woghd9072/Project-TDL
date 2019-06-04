package com.jaehong.service;

import com.jaehong.domain.CommentVO;
import com.jaehong.domain.ToDoList;
import com.jaehong.domain.ToDoListComment;
import com.jaehong.repository.ToDoListCommentRepository;
import com.jaehong.repository.ToDoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class ToDoListCommentService {

    @Autowired
    ToDoListCommentRepository toDoListCommentRepository;

    @Autowired
    ToDoListRepository toDoListRepository;

    public CommentVO commentService(Map<String, String> map) {
        ToDoListComment toDoListComment = new ToDoListComment();
        ToDoList toDoList = toDoListRepository.findByIdx(Integer.parseInt(map.get("idx")));
        toDoListComment.setComment(map.get("comment"));
        toDoListComment.setCreatedDate(LocalDateTime.now());
        toDoListComment.setUpdatedDate(null);
        toDoListComment.setToDoList(toDoList);
        toDoList.add(toDoListComment);
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
