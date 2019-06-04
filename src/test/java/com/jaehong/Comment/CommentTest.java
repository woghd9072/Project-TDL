package com.jaehong.Comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaehong.domain.ToDoList;
import com.jaehong.domain.ToDoListComment;
import com.jaehong.domain.UserDto;
import com.jaehong.repository.ToDoListCommentRepository;
import com.jaehong.repository.ToDoListRepository;
import com.jaehong.service.ToDoListCommentService;
import com.jaehong.service.ToDoListService;
import com.jaehong.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentTest {

    @Autowired
    ToDoListCommentService toDoListCommentService;

    @Autowired
    ToDoListService toDoListService;

    @Autowired
    UserService userService;

    @Autowired
    ToDoListRepository toDoListRepository;

    @Autowired
    ToDoListCommentRepository toDoListCommentRepository;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    private UserDetails userDetails;

    private ToDoList toDoList = new ToDoList();

    @Before
    public void init() throws Exception {

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .alwaysDo(print())
                .build();

        UserDto userDto = new UserDto();
        userDto.setId("user");
        userDto.setPwd("1234");
        userDto.setEmail("user@ks.ac.kr");

        mockMvc
                .perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)));

        userDetails = userService.loadUserByUsername("user");

        mockMvc.perform(get("/tdl/list").with(user(userDetails)))
                .andExpect(status().isOk());

        toDoList.setDescription("user1");

        mockMvc.perform(post("/tdl").with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDoList)))
                .andExpect(status().isCreated());

    }

    @Test
    public void postTest() throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("comment", "re:1");
        map.put("idx", "1");

        mockMvc.perform(post("/comment").with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(map)))
                .andExpect(status().isCreated());

        ToDoListComment toDoListComment = toDoListCommentRepository.findAll().get(0);
        ToDoList list = toDoListRepository.findAll().get(0);
        assertThat(toDoListComment.getToDoList().getIdx()).isEqualTo(list.getIdx());

        ToDoList toDoList1 = new ToDoList();
        toDoList1.setDescription("user2");

        mockMvc.perform(post("/tdl").with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDoList1)))
                .andExpect(status().isCreated());

        map.clear();
        map.put("comment", "re:2");
        map.put("idx", "2");

        mockMvc.perform(post("/comment").with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(map)))
                .andExpect(status().isCreated());

    }

    @Test
    public void putTest() throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("comment", "re:1");
        map.put("idx", "1");

        mockMvc.perform(post("/comment").with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(map)))
                .andExpect(status().isCreated());

        mockMvc.perform(put("/comment/1").with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content("re:1-1"))
                .andExpect(status().isOk());

        ToDoListComment toDoListComment = toDoListCommentRepository.findAll().get(0);
        assertThat(toDoListComment.getComment()).isEqualTo("re:1-1");
        assertThat(toDoListComment.getUpdatedDate()).isNotNull();
    }

    @Test
    public void deleteTest() throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("comment", "re:1");
        map.put("idx", "1");

        mockMvc.perform(post("/comment").with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(map)))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/comment/1").with(user(userDetails)))
                .andExpect(status().isOk());

    }
}
