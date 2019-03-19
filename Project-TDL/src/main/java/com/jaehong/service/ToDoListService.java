package com.jaehong.service;

import com.jaehong.domain.ToDoList;
import com.jaehong.repository.ToDoListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ToDoListService {

    @Autowired
    ToDoListRepository toDoListRepository;

    public List<ToDoList> findList() {
        return toDoListRepository.findAll();
    }

    public ToDoList postService(ToDoList toDoList) {
        toDoList.setCreatedDate(LocalDateTime.now());
        toDoList.setStatus(false);
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
        ToDoList CtodoList = toDoListRepository.getOne(idx);
        if(!CtodoList.getStatus()) {
            CtodoList.setStatus(true);
            CtodoList.setCompletedDate(LocalDateTime.now());
        } else {
            CtodoList.setStatus(false);
            CtodoList.setCompletedDate(null);
        }
        toDoListRepository.save(CtodoList);
    }
}
