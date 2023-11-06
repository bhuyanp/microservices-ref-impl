package com.example.microservice.customer.service;

import com.example.microservice.customer.dto.CustomerDTO;
import com.example.microservice.customer.model.Customer;
import com.example.microservice.customer.repo.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {

    private final CustomerRepo customerRepo;


    public CustomerDTO addCustomer(CustomerDTO customerDTO){
        if(customerRepo.findById(customerDTO.getId()).isPresent())return customerDTO;

        Customer customer = customerRepo.save(getCustomer(customerDTO));
        return getCustomerDTO(customer);
    }

    public List<CustomerDTO> getAllCustomers(){
        return customerRepo.findAll()
                .stream()
                .map(customer -> getCustomerDTO(customer))
                .toList();
    }
    public Optional<CustomerDTO> getCustomer(Integer id) {
        return customerRepo.findById(id).map(customer -> getCustomerDTO(customer));
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
