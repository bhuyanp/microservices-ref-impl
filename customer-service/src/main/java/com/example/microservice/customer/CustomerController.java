package com.example.microservice.customer;

import com.example.microservice.customer.dto.CustomerDTO;
import com.example.microservice.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RestController
@RequiredArgsConstructor
@EnableJpaRepositories
public class CustomerController {

    private final CustomerService customerService;

    public static final String URI = "/api/customer";
    @Value("${server.port}")
    private int serverPort;

    @GetMapping
    public String home() {
        return "Customer Microservice";
    }



    @GetMapping(URI)
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDTO> getCustomers() {
        return customerService.getAllCustomers()
                .stream().map(hateosLinkFunction)
                .toList();
    }

    @GetMapping(URI+"/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCustomer(@PathVariable String id) {
        Optional<CustomerDTO> optionalCustomerDTO = customerService.getCustomer(id)
                .map(hateosLinkFunction);
        return optionalCustomerDTO.orElse(null);
    }

    @PostMapping(URI)
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO addCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.addCustomer(customerDTO);
    }

    private Function<CustomerDTO, CustomerDTO> hateosLinkFunction = it -> it.add(Link.of(URI+"/" + it.getId()));
}
