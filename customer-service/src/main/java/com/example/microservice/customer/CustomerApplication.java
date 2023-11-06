package com.example.microservice.customer;

import com.example.microservice.customer.model.Customer;
import com.example.microservice.customer.repo.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.UUID;

@SpringBootApplication
public class CustomerApplication {

	@Autowired
	private CustomerRepo customerRepo;

	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
	}

}
