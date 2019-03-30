package com.jaehong;

import com.jaehong.domain.User;
import com.jaehong.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(UserRepository userRepository) throws Exception {
        return args -> {
            User user = userRepository.save(User.builder()
                .id("jaehong").pwd("test").email("test").build());
        };
    }
}
