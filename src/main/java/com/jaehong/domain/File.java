package com.jaehong.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer idx;

    @Column
    private String name;

    @Column
    private String size;

    @Column
    private String url;

    @Column
    private LocalDateTime uploadedDate;

    @ManyToOne
    private ToDoList toDoList;

    public File(String name, String size, String url, LocalDateTime uploadedDate, ToDoList toDoList) {
        this.name = name;
        this.size = size;
        this.url = url;
        this.uploadedDate = uploadedDate;
        this.toDoList = toDoList;
    }
}
