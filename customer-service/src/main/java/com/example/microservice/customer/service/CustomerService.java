package com.example.microservice.customer.service;

import com.example.microservice.customer.dto.CustomerDTO;
import com.example.microservice.customer.dto.CustomerRequest;
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


    public CustomerDTO addCustomer(CustomerRequest customerRequest){
        Customer customer = customerRepo.save(getCustomer(customerRequest));
        return getCustomerDTO(customer);
    }

    public List<CustomerDTO> getAllCustomers(){
        return customerRepo.findAll()
                .stream()
                .map(this::getCustomerDTO)
                .toList();
    }
    public Optional<CustomerDTO> getCustomer(Integer id) {
        return customerRepo.findById(id).map(this::getCustomerDTO);
    }

    private Customer getCustomer(CustomerRequest customerRequest){
        return Customer.builder()
                .firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .email(customerRequest.getEmail())
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
