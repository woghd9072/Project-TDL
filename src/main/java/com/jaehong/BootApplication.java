package com.jaehong;

import com.jaehong.file.FileService;
import com.jaehong.file.property.FileProperty;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(FileProperty.class)
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }

    @Bean
    CommandLineRunner init(FileService fileService) {
        return args -> {
            fileService.deleteAll();
            fileService.init();
        };
    }
}
