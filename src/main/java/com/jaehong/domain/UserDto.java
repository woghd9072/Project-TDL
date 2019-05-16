package com.jaehong.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class UserDto implements Serializable {

    @NotBlank(message = "아이디를 입력하세요 ㅡㅡ")
    private String id;

    @NotBlank(message = "비밀번호를 입력하세요 ㅡㅡ")
    private String pwd;

    @NotBlank(message = "이메일을 입력하세요 ㅡㅡ")
    @Email(message = "이메일 형식을 지키세요 ㅡㅡ")
    private String email;

    public User toEntity() {
        User user = new User();
        user.setId(this.id);
        user.setEmail(this.email);
        user.setPwd(this.pwd);
        return user;
    }
}
