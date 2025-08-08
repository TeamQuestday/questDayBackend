package com.project.questday;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class QuestDayApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuestDayApplication.class, args);
    }

}
