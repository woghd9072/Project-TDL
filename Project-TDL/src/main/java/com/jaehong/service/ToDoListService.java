package com.jaehong.service;

import com.jaehong.domain.ToDoList;
import com.jaehong.domain.User;
import com.jaehong.repository.ToDoListRepository;
import com.jaehong.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ToDoListService {

    @Autowired
    ToDoListRepository toDoListRepository;

    public List<ToDoList> findList(Integer idx) {
        return toDoListRepository.findByUserIdx(idx);
    }

    public ToDoList postService(ToDoList toDoList, User currentUser) {
        toDoList.setCreatedDate(LocalDateTime.now());
        toDoList.setStatus(false);
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
