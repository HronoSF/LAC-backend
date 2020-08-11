package com.github.hronosf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@EnableScheduling
@SpringBootApplication
public class LacApplication {

    public static void main(String[] args) {
        SpringApplication.run(LacApplication.class, args);
    }
}
