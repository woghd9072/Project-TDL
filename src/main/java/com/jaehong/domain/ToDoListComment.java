package com.jaehong.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class ToDoListComment implements Comparable<ToDoListComment> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer idx;

    @Column
    private String comment;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime updatedDate;

    @ManyToOne
    private ToDoList toDoList;

    @Builder
    public ToDoListComment(String comment, LocalDateTime createdDate, LocalDateTime updatedDate, ToDoList toDoList) {
        this.comment = comment;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.toDoList = toDoList;
    }

    public void update(String comment) {
        this.comment = comment;
        this.createdDate = this.getCreatedDate();
        this.updatedDate = LocalDateTime.now();
        this.toDoList = this.getToDoList();
    }

    @Override
    public int compareTo(ToDoListComment toDoListComment) {
        return idx.compareTo(toDoListComment.idx);
    }
}
