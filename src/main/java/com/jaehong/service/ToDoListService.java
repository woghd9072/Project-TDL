package com.jaehong.service;

import com.jaehong.domain.ToDoList;
import com.jaehong.domain.ToDoListComment;
import com.jaehong.domain.User;
import com.jaehong.repository.ToDoListCommentRepository;
import com.jaehong.repository.ToDoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class ToDoListService {

    @Autowired
    ToDoListRepository toDoListRepository;

    @Autowired
    ToDoListCommentRepository toDoListCommentRepository;

    public List<ToDoList> findList(Integer idx) {
        List<ToDoList> list = toDoListRepository.findByUserIdx(idx);
        for(ToDoList toDoList : list) {
            List<ToDoListComment> commentList = toDoList.getToDoListComment();
            Collections.sort(commentList);
        }
        return list;
    }

    public ToDoList findTdl(Integer idx) {
        return toDoListRepository.findByIdx(idx);
    }

    public ToDoList postService(ToDoList toDoList, User currentUser) {
        toDoList.setCreatedDate(LocalDateTime.now());
        toDoList.setStatus(false);
        toDoList.setToDoListComment(toDoListCommentRepository.findByToDoListIdx(toDoList.getIdx()));
        currentUser.add(toDoList);
        ToDoList postTDL = toDoListRepository.save(toDoList);
        return postTDL;
    }

    public void deleteService(Integer idx) {
        toDoListRepository.deleteById(idx);
    }

    public void updateService(Integer idx, String description) {
        ToDoList persistList = toDoListRepository.getOne(idx);
        persistList.update(description);
        toDoListRepository.save(persistList);
    }

    public void completeService(Integer idx) {
        ToDoList completeList = toDoListRepository.getOne(idx);
        completeList.setStatus(completeList.getStatus() ? false : true);
        completeList.setCompletedDate(completeList.getStatus() ? LocalDateTime.now() : null);
        toDoListRepository.save(completeList);
    }

}
