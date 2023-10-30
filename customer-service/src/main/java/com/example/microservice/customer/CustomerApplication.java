package com.example.microservice.customer;

import com.example.microservice.customer.model.Customer;
import com.example.microservice.customer.repo.CustomerRepo;
import com.example.microservice.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class CustomerApplication implements CommandLineRunner {

	@Autowired
	private CustomerRepo customerRepo;

	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		customerRepo.deleteAll();
		customerRepo.save(new Customer(UUID.randomUUID().toString(),"Prasanta", "Bhuyan", "p.bhuyan@gmail.com"));
		customerRepo.save(new Customer(UUID.randomUUID().toString(),"PK", "Bhuyan", "prasanta.k.bhuyan@gmail.com"));
	}
}
