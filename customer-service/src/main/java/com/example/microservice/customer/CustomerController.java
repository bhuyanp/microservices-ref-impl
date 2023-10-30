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
@RequestMapping("/")
@RequiredArgsConstructor
@EnableJpaRepositories
public class CustomerController {

    private final CustomerService customerService;

    @Value("${server.port}")
    private int serverPort;

    @GetMapping
    public String home() {
        return "Customer Microservice";
    }

    @GetMapping("/api/customer")
    public List<Link> getCustomer() {
        return List.of(Link.of(getBaseURL()+"/get", "Get All Customers"));
    }

    @GetMapping("/api/customer/get")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDTO> getCustomers() {
        return customerService.getAllCustomers()
                .stream().map(hateosLinkFunction)
                .toList();
    }

    @GetMapping("/api/customer/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCustomer(@PathVariable String id) {
        Optional<CustomerDTO> optionalCustomerDTO = customerService.getCustomer(id)
                .map(hateosLinkFunction);
        return optionalCustomerDTO.orElse(null);
    }

    private Function<CustomerDTO, CustomerDTO> hateosLinkFunction = it -> it.add(Link.of(getBaseURL()+"/get/" + it.getId()));

    @PostMapping("/api/customer")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO addCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.addCustomer(customerDTO);
    }

    private String getBaseURL(){
        return "http://localhost:"+serverPort+"/api/customer";
    }
}
