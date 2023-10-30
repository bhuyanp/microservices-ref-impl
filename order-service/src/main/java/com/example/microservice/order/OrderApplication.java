package com.example.microservice.order;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.swing.*;

@SpringBootApplication
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    @Bean
    public CommandLineRunner getCommandLineRunner(OrderRepo orderRepo) {
        return args -> {
            System.out.println(orderRepo.findAll());
        };
    }
}
