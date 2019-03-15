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

    public void postService(String description) {
        toDoListRepository.save(ToDoList.builder()
            .description(description.replace("description=", ""))
            .status(false)
            .createdDate(LocalDateTime.now()).build());
    }

    public void deleteService(Integer idx) {
        toDoListRepository.deleteById(idx);
    }
}
