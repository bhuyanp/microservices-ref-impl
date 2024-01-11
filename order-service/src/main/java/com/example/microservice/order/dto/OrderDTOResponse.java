package com.example.microservice.order.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTOResponse extends RepresentationModel<OrderDTOResponse> {
    private String orderId;
    private String orderById;
    private String orderByName;
    private String orderByEmail;
    private Date orderDate;
    private List<OrderLineItem> orderLineItems;
}
