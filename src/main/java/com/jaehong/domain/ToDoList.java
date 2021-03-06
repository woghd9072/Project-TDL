package com.jaehong.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class ToDoList implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer idx;

    @Column
    private String description;

    @Column
    private Boolean status;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime completedDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "toDoList")
    private List<ToDoListComment> toDoListComment = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "toDoList")
    private List<File> files = new ArrayList<>();

    @ManyToOne
    private User user;

    @Builder
    public ToDoList(String description, Boolean status, LocalDateTime createdDate, LocalDateTime completedDate, List<ToDoListComment> toDoListComment, User user) {
        this.description = description;
        this.status = status;
        this.createdDate = createdDate;
        this.completedDate = completedDate;
        this.toDoListComment = toDoListComment;
        this.user = user;
    }

    public void update(String description) {
        this.description = description;
        this.status = this.getStatus();
        this.createdDate = this.getCreatedDate();
        this.completedDate = this.getCompletedDate();
        this.user = this.getUser();
    }

    public void add(ToDoListComment toDoListComment1) {
        toDoListComment1.setToDoList(this);
        this.toDoListComment.add(toDoListComment1);
    }

    public void add(File file) {
        file.setToDoList(this);
        this.files.add(file);
    }
}
