package com.example.microservice.product.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductAvailabilityDTOResponse {
    private String productId;
    private int availableCount;
}
