package com.jaehong.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer idx;

    @Column
    private String id;

    @Column
    private String pwd;

    @Builder
    public User(String id, String pwd) {
        this.id = id;
        this.pwd = pwd;
    }
}
