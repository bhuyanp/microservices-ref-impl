package com.example.microservice.customer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
public class CustomerDTO extends RepresentationModel<CustomerDTO> {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
}
