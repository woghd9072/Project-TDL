package com.jaehong.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@NoArgsConstructor
@Setter
@Getter
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Column(unique = true)
    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    @Column
    private LocalDateTime sendedDate;

    public PasswordResetToken(String token, User user, LocalDateTime sendedDate) {
        this.token = token;
        this.user = user;
        this.sendedDate = sendedDate;
    }
}
