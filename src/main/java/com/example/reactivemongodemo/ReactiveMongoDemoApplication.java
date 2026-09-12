package com.example.reactivemongodemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@SpringBootApplication
@EnableReactiveMongoAuditing
public class ReactiveMongoDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveMongoDemoApplication.class, args);
    }

}
