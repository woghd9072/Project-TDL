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
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long idx;

    @Column
    private boolean status;

    @Column
    private String description;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime completedDate;

    @Builder
    public ToDo(boolean status, String description, LocalDateTime createdDate, LocalDateTime completedDate) {
        this.status = status;
        this.description = description;
        this.createdDate = createdDate;
        this.completedDate = completedDate;
    }
}
