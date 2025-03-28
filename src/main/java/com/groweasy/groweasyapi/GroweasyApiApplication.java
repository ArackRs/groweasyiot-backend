package com.groweasy.groweasyapi;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Log4j2
@EnableJpaAuditing
@SpringBootApplication
public class GroweasyApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GroweasyApiApplication.class, args);
    }

}
