package com.jaehong.ToDoList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaehong.domain.ToDoList;
import com.jaehong.domain.UserDto;
import com.jaehong.repository.ToDoListRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ToDoListTest {

    @Autowired
    ToDoListService toDoListService;

    @Autowired
    UserService userService;

    @Autowired
    ToDoListRepository toDoListRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDetails userDetails;

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
    }

    @Test
    public void GetTest() throws Exception{

        mockMvc.perform(get("/tdl/list").with(user(userDetails)))
                .andExpect(view().name("/tdl/list"))
                .andExpect(status().isOk());

    }

    @Test
    public void PostTest() throws Exception {

        ToDoList toDoList = new ToDoList();
        toDoList.setDescription("user1");

        mockMvc.perform(post("/tdl").with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDoList)))
                .andExpect(status().isCreated());

        ToDoList list = toDoListRepository.findAll().get(0);
        assertThat(list).isNotNull();
        assertThat(list.getDescription()).isEqualTo(toDoList.getDescription());
        assertThat(list.getCreatedDate()).isNotNull();
        assertThat(list.getCompletedDate()).isNull();
        assertThat(list.getStatus()).isFalse();
    }

    @Test
    public void PutTest() throws Exception {

        ToDoList toDoList = new ToDoList();
        toDoList.setDescription("user1");

        mockMvc.perform(post("/tdl").with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDoList)))
                .andExpect(status().isCreated());

        mockMvc.perform(put("/tdl/1").with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content("1234"))
                .andExpect(status().isOk());

        mockMvc.perform(put("/tdl/complete/1").with(user(userDetails)))
                .andExpect(status().isOk());

        ToDoList list = toDoListRepository.findAll().get(0);
        assertThat(list.getStatus()).isTrue();
        assertThat(list.getCompletedDate()).isNotNull();

    }

    @Test
    public void DeleteTest() throws Exception {

        ToDoList toDoList = new ToDoList();
        toDoList.setDescription("user1");

        mockMvc.perform(post("/tdl").with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDoList)))
                .andExpect(status().isCreated());

        mockMvc.perform(delete("/tdl/1").with(user(userDetails)))
                .andExpect(status().isOk());

    }

}
