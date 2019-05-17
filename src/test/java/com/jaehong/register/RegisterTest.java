package com.jaehong.register;

import com.jaehong.repository.UserRepository;
import com.jaehong.service.RegisterService;
import com.jaehong.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegisterTest {

    @Autowired
    UserService userService;

    @Autowired
    RegisterService registerService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void GetTest() throws Exception {

        //GetMapping Test
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk());

        //없는 URL Test
        mockMvc.perform(get("/rego"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void PostTest() throws Exception {

        // 중복 X
        mockMvc.perform(post("/register/confirm/id")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"user1\"}"))
                .andExpect(status().isOk());

        // Email 중복 X
        mockMvc.perform(post("/register/confirm/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"user1@ks.ac.kr\"}"))
                .andExpect(status().isOk());

        // Id 형식 X
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"user!\"}"))
                .andExpect(status().isBadRequest());

        // Id 공백
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"\"}"))
                .andExpect(status().isBadRequest());

        // Id 길이 4 미만
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"use\"}"))
                .andExpect(status().isBadRequest());

        // Id 길이 12 초과
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"user!!!!!!!!!!!\"}"))
                .andExpect(status().isBadRequest());

        // Password 형식 X
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pwd\":\"user!\"}"))
                .andExpect(status().isBadRequest());

        // Password 공백
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pwd\":\"\"}"))
                .andExpect(status().isBadRequest());

        // Password 길이 3 미만
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pwd\":\"use\"}"))
                .andExpect(status().isBadRequest());

        // Password 길이 12 초과
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pwd\":\"user!!!!!!!!!!!\"}"))
                .andExpect(status().isBadRequest());

        // Email 형식 X
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"user!\"}"))
                .andExpect(status().isBadRequest());

        // Email 공백
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"\"}"))
                .andExpect(status().isBadRequest());

        // 정상 회원가입
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"user1\",\"pwd\":\"12345ab\",\"email\":\"user1@ks.ac.kr\"}"))
                .andExpect(status().isCreated());

        // Id 중복 O
        mockMvc.perform(post("/register/confirm/id")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"user1\"}"))
                .andExpect(status().isBadRequest());

        // Email 중복 O
        mockMvc.perform(post("/register/confirm/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"user1@ks.ac.kr\"}"))
                .andExpect(status().isBadRequest());

        // 비정상 회원가입
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"\",\"pwd\":\"12345ab\",\"email\":\"user1@ks.ac.kr\"}"))
                .andExpect(status().isBadRequest());
    }
}
