package com.example.microservice.customer.service;

import com.example.microservice.customer.dto.CustomerDTO;
import com.example.microservice.customer.model.Customer;
import com.example.microservice.customer.repo.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepo customerRepo;


    public CustomerDTO addCustomer(CustomerDTO customerDTO){
        Customer customer = customerRepo.save(getCustomer(customerDTO));
        return getCustomerDTO(customer);
    }

    private Customer getCustomer(CustomerDTO customerDTO){
        return Customer.builder()
                .id(customerDTO.getId())
                .firstName(customerDTO.getFirstName())
                .lastName(customerDTO.getLastName())
                .email(customerDTO.getEmail())
                .build();
    }

    private CustomerDTO getCustomerDTO(Customer customer){
        return CustomerDTO.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail()).
                build();
    }
}
