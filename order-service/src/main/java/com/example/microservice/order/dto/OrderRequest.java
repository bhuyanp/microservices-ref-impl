package com.example.microservice.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    @NotBlank(message = "Customer id is mandatory")
    private String orderById;
    @NotBlank(message = "Customer name is mandatory")
    private String orderByName;
    @NotBlank(message = "Customer email is mandatory")
    private String orderByEmail;
    private Date orderDate;
    @NotEmpty(message = "At least one item is required in the order.")
    private List<OrderLineItem> orderLineItems;

}
