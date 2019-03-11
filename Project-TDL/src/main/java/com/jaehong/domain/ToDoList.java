package com.jaehong.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table
public class ToDoList {

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

    @Builder
    public ToDoList(String description, Boolean status, LocalDateTime createdDate, LocalDateTime completedDate) {
        this.description = description;
        this.status = status;
        this.createdDate = createdDate;
        this.completedDate = completedDate;
    }
}
