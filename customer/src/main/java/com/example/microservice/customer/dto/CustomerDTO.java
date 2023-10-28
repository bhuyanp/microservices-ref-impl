package com.example.microservice.customer.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
}
