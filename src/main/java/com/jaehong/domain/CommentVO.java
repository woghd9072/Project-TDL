package com.jaehong.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class CommentVO implements Serializable {

    private Integer idx;

    private String comment;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;
}
