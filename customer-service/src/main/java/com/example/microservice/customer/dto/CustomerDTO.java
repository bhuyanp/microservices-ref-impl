package com.example.microservice.customer.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
public class CustomerDTO extends RepresentationModel<CustomerDTO> {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
}
