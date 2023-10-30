package com.example.microservice.product.model;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import java.math.BigDecimal;

@Data
@Document
@RequiredArgsConstructor
public class Product {
    @Id
    @GeneratedValue()
    private String id;
    private final String title;
    private final String description;
    private final BigDecimal price;
}
