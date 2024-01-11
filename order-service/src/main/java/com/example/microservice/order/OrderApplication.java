package com.example.microservice.order;

import com.example.microservice.order.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = {
        //getting common open api config from root project
        "com.example.microservice.openapidoc",
        "com.example.microservice.order"
})
@EnableDiscoveryClient
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
            int port = "docker".equals(profile)?8080:9090;
            System.out.printf("Eureka: http://localhost:%s/eureka/web%n", port);
            System.out.printf("Swagger UI(wait 30s): http://localhost:%s/order%n", port);
        };
    }

    @Value("${spring.profiles.active:unknown}")
    String profile;
}
