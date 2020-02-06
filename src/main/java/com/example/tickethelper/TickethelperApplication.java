package com.example.tickethelper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TickethelperApplication {

    public static void main(String[] args) {
        SpringApplication.run(TickethelperApplication.class, args);
    }

}
