package com.example.microservice.product.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTOResponse extends RepresentationModel<ProductDTOResponse> {
    private String id;
    private String title;
    private String description;
    private BigDecimal price;
    private Boolean available;
}
