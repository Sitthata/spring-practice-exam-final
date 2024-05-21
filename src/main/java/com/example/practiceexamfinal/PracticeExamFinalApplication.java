package com.example.practiceexamfinal;

import com.example.practiceexamfinal.properties.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class PracticeExamFinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(PracticeExamFinalApplication.class, args);
    }

}
