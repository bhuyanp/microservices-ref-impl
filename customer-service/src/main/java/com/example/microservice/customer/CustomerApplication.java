package com.example.microservice.customer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.annotation.*;
import java.util.Arrays;

@Controller
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = {
		//getting common open api config from root project
		"com.example.microservice.openapidoc",
		"com.example.microservice.customer"
})
public class CustomerApplication {

	@Value("${spring.profiles.active:unknown}")
	String profile;

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
			int port = "docker".equals(profile)?8080:9090;
			System.out.printf("Eureka: http://localhost:%s/eureka/web%n", port);
			System.out.printf("Swagger UI(wait 30s): http://localhost:%s/customer%n", port);
		};
	}

}
