package com.example.microservice.customer;

import com.example.microservice.customer.dto.CustomerDTO;
import com.example.microservice.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@EnableJpaRepositories
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public String home(){
        return "Customer Microservice";
    }

    @GetMapping("/api/customer")
    public String getCustomer(){
        return "fdgdsf";
    }

    @PostMapping("/api/customer")
    @ResponseStatus()
    public CustomerDTO addCustomer(@RequestBody CustomerDTO customerDTO){
        return customerService.addCustomer(customerDTO);
    }
}
