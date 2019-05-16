package com.jaehong.repository;

import com.jaehong.domain.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoListRepository extends JpaRepository<ToDoList, Integer> {
    List<ToDoList> findByUserIdx(Integer idx);
    ToDoList findByIdx(Integer idx);
}
