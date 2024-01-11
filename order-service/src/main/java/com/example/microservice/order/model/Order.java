package com.example.microservice.order.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@Data
@Document
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String orderById;
    private String orderByName;
    private String orderByEmail;
    private Date orderDate;
    private List<OrderLineItem> orderLineItems;
}
