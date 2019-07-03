package com.jaehong.file;

import com.jaehong.domain.ToDoList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer idx;

    @Column
    private String name;

    @Column
    private String url;

    @Column
    private long size;

    @Column
    private LocalDateTime uploadedDate;

    @ManyToOne
    private ToDoList toDoList;
}
