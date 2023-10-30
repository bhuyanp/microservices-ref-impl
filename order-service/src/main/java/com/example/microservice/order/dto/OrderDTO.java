package com.example.microservice.order.dto;

import com.example.microservice.order.model.OrderLineItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;


import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private String orderById;
    private String orderByName;
    private String orderByEmail;
    private Date orderDate;
    private List<OrderLineItemDTO> orderLineItems;

}
