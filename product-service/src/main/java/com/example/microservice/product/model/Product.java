package com.example.microservice.product.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import java.math.BigDecimal;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue()
    private String id;
    private String title;
    private String description;
    private BigDecimal price;
    private int availableInventory;
}
