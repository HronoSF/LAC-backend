package com.github.hronosf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@EnableScheduling
@EnableAspectJAutoProxy
@SpringBootApplication(scanBasePackages = {"com.github.hronosf"})
public class LacApplication {

    public static void main(String[] args) {
        SpringApplication.run(LacApplication.class, args);
    }
}
