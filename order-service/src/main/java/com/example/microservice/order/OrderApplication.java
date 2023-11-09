package com.example.microservice.order;

import com.example.microservice.order.repo.OrderRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SpringBootApplication
@ComponentScan(basePackages = {
        //getting common open api config from root project
        "com.example.microservice.config",
        "com.example.microservice.order"
})
public class OrderApplication {


    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    @GetMapping(path = { "/", "/actuator/info" })
    public String home() {
        return "redirect:/swagger-ui.html";
    }

    @Bean
    public CommandLineRunner getCommandLineRunner(OrderRepo orderRepo) {
        return args -> {

            System.out.println(orderRepo.findAll());
            System.out.println("Eureka: http://localhost:8761/");
            System.out.println("API Gateway(wait 30s): http://localhost:8080/api/v1/order");
        };
    }
}
