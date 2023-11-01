package com.example.microservice.order;

import com.example.microservice.order.repo.OrderRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    @Bean
    public CommandLineRunner getCommandLineRunner(OrderRepo orderRepo) {
        return arƒgs -> {
            System.out.println(orderRepo.findAll());
        };
    }
}
