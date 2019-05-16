package com.jaehong.repository;

import com.jaehong.domain.ToDoListComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoListCommentRepository extends JpaRepository<ToDoListComment, Integer> {
    List<ToDoListComment> findByToDoListIdx(Integer idx);
    List<ToDoListComment> findAllOrderByIdx(Integer idx);
}
