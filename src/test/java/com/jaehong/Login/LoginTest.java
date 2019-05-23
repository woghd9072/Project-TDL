package com.jaehong.Login;

import com.jaehong.domain.UserDto;
import com.jaehong.repository.UserRepository;
import com.jaehong.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Test
    public void SuccessTest() throws Exception {

        // user 정보
        UserDto userDto = new UserDto();
        userDto.setId("user");
        userDto.setPwd("1234");
        userDto.setEmail("user@ks.ac.kr");

        userService.save(userDto.toEntity());

        // set up
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        // login page 확인
        mockMvc.perform(get("/login"))
                .andExpect(unauthenticated())
                .andExpect(status().isOk());

        // 틀린 아이디로 로그인 확인
        mockMvc.perform(formLogin().user("user1"))
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(unauthenticated())
                .andExpect(status().is3xxRedirection());

        // 비밀번호 없이 로그인 확인
        mockMvc.perform(formLogin().user("user"))
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(unauthenticated())
                .andExpect(status().is3xxRedirection());

        // 아이디 없이 로그인 확인
        mockMvc.perform(formLogin().password("1234"))
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(unauthenticated())
                .andExpect(status().is3xxRedirection());

        // 로그인 없이 list 화면 확인
        mockMvc.perform(get("/tdl/list"))
                .andExpect(redirectedUrl("http://localhost/login"))
                .andExpect(unauthenticated())
                .andExpect(status().is3xxRedirection());

        // UserDetails 타입 유저
        UserDetails userDetails = userService.loadUserByUsername("user");

        // 로그인 성공 확인
        mockMvc.perform(get("/").with(user(userDetails)))
                .andExpect(redirectedUrl("/tdl/list"))
                .andExpect(authenticated())
                .andExpect(status().is3xxRedirection());

        // model 존재 여부 확인
        mockMvc.perform(get("/tdl/list").with(user(userDetails)))
                .andExpect(model().attributeExists("tdlList"))
                .andExpect(authenticated())
                .andExpect(status().isOk());

    }
}
