package com.jaehong;

import com.jaehong.domain.ToDoList;
import com.jaehong.repository.ToDoListRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootApplication
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(ToDoListRepository toDoListRepository) {
        return args -> {
            IntStream.rangeClosed(1, 51).forEach(index ->
                    toDoListRepository.save(ToDoList.builder()
                        .description("설명"+index)
                        .status(true)
                        .createdDate(LocalDateTime.now())
                        .build()));
        };
    }

}
