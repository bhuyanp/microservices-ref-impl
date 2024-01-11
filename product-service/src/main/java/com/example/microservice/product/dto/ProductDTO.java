package com.example.microservice.product.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO extends RepresentationModel<ProductDTO> {
    private String title;
    private String description;
    private BigDecimal price;
    private int availableInventory;
}

