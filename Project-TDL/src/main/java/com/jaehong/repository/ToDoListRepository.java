package com.jaehong.repository;

import com.jaehong.domain.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoListRepository extends JpaRepository<ToDoList, Integer> {
}
