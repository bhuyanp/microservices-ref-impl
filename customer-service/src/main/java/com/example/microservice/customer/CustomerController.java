package com.example.microservice.customer;

import com.example.microservice.customer.dto.CustomerDTO;
import com.example.microservice.customer.dto.CustomerRequest;
import com.example.microservice.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@RestController
@RequiredArgsConstructor
@EnableJpaRepositories
@Tag(name = CustomerController.TAG)
@SecurityRequirement(name = "Oauth2Password")
@Slf4j
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
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(path = URI, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomerDTO> addCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        CustomerDTO customerDTO = customerService.addCustomer(customerRequest);
        customerDTO = hateosLinkFunction.apply(customerDTO);
        return ResponseEntity.created(java.net.URI.create(URI + "/" + customerDTO.getId())).body(customerDTO);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.warn("Customer validation messages: {}",errors);
        return errors;
    }

    private Function<CustomerDTO, CustomerDTO> hateosLinkFunction = it -> it.add(Link.of(URI + "/" + it.getId()));
}
