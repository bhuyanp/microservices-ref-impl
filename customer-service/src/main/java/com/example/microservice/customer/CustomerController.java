package com.example.microservice.customer;

import com.example.microservice.customer.dto.CustomerDTO;
import com.example.microservice.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;

@RestController
@RequiredArgsConstructor
@EnableJpaRepositories
@Tag(name = CustomerController.TAG)
@SecurityRequirement(name = "Oauth2Password")
public class CustomerController {
    public static final String TAG = "Customer Service";

    private final CustomerService customerService;

    public static final String URI = "/api/v1/customer";

    @GetMapping(path = URI, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDTO> getCustomers() {
        return customerService.getAllCustomers()
                .stream().map(hateosLinkFunction)
                .toList();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = URI + "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Integer id) {
        return customerService.getCustomer(id)
                .map(hateosLinkFunction)
                .map(customerDTO -> ResponseEntity.ok(customerDTO))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(path = URI, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO addCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.addCustomer(customerDTO);
    }

    private Function<CustomerDTO, CustomerDTO> hateosLinkFunction = it -> it.add(Link.of(URI + "/" + it.getId()));
}
