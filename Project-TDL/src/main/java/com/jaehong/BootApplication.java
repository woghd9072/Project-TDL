package com.jaehong;

import com.jaehong.domain.ToDoList;
import com.jaehong.repository.ToDoListRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootApplication
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner runner(ToDoListRepository toDoListRepository) {
//        return args -> {
//            IntStream.rangeClosed(1, 20).forEach(index ->
//                    toDoListRepository.save(ToDoList.builder()
//                        .description("To Do List by Spring Boot"+index)
//                        .status(true)
//                        .createdDate(LocalDateTime.now())
//                        .build()));
//        };
//    }

//    @Bean
//    public FilterRegistrationBean filterRegistrationBean() {
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//        characterEncodingFilter.setEncoding("UTF-8");
//        registrationBean.setFilter(characterEncodingFilter);
//        return registrationBean;
//    }

}
