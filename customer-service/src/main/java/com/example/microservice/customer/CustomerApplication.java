package com.example.microservice.customer;

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
		"com.example.microservice.customer"
})
public class CustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
	}

	@GetMapping(path = { "/", "/actuator/info" })
	public String home() {
		return "redirect:/swagger-ui/index.html";
	}

	@Bean
	CommandLineRunner getCommandLineRunner(){
		return args->{
			System.out.println("Eureka: http://localhost:8080/eureka/web");
			System.out.println("Swagger UI(wait 30s): http://localhost:8080/customer");
		};
	}

}
