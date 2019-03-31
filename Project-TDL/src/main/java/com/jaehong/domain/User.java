package com.jaehong.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
@Entity
@Table
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer idx;

    @Column
    private String id;

    @Column
    private String pwd;

    @Column
    private String email;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private List<ToDoList> toDoList = new ArrayList<>();

    @Builder
    public User(String id, String pwd, String email, List<ToDoList> toDoList) {
        this.id = id;
        this.pwd = pwd;
        this.email = email;
        this.toDoList = toDoList;
    }

    public void add(ToDoList toDoLists) {
        toDoLists.setUser(this);
        this.toDoList.add(toDoLists);
    }
}
