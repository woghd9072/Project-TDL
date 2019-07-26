package com.jaehong.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PasswordForgot {

    @NotBlank
    private String Id;

    @Email
    @NotBlank
    private String email;

}
