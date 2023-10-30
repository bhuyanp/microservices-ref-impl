package com.example.microservice.order;

import com.example.microservice.order.dto.OrderDTO;
import com.example.microservice.order.dto.OrderDTOResponse;
import com.example.microservice.order.dto.OrderLineItemDTO;
import com.example.microservice.order.model.Order;
import com.example.microservice.order.model.OrderLineItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;

    public OrderDTOResponse addOrder(OrderDTO orderDTO) {
        Order newlyCreatedOrder =
                orderRepo.save(Order.builder()
                        .orderById(orderDTO.getOrderById())
                        .orderByEmail(orderDTO.getOrderByEmail())
                        .orderByName(orderDTO.getOrderByName())
                        .orderDate(orderDTO.getOrderDate())
                        .orderLineItems(orderDTO.getOrderLineItems()
                                .stream()
                                .map(orderLineItemDTO ->
                                        new OrderLineItem(orderLineItemDTO.getProductId(), orderLineItemDTO.getQuantity())).toList())
                        .build());
        return getOrderDTOResponse(newlyCreatedOrder);
    }

    public Optional<OrderDTOResponse> getOrder(String id) {
        return orderRepo.findById(id).map(this::getOrderDTOResponse);
    }

    public List<OrderDTOResponse> getAllOrders() {
        return orderRepo.findAll().stream().map(this::getOrderDTOResponse).toList();

    }

    private OrderDTOResponse getOrderDTOResponse(Order order) {
        return OrderDTOResponse.builder()
                .orderById(order.getOrderById())
                .orderByEmail(order.getOrderByEmail())
                .orderByName(order.getOrderByName())
                .orderDate(order.getOrderDate())
                .orderLineItems(order.getOrderLineItems()
                        .stream()
                        .map(orderLineItem ->
                                new OrderLineItemDTO(orderLineItem.getProductId(), orderLineItem.getQuantity())).toList())
                .build();
    }

}
