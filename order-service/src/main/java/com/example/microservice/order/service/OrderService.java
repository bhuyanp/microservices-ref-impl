package com.example.microservice.order.service;

import com.example.microservice.order.dto.OrderDTO;
import com.example.microservice.order.dto.OrderDTOResponse;
import com.example.microservice.order.dto.OrderLineItemDTO;
import com.example.microservice.order.dto.ProductAvailabilityDTOResponse;
import com.example.microservice.order.exception.OrderException;
import com.example.microservice.order.model.Order;
import com.example.microservice.order.model.OrderLineItem;
import com.example.microservice.order.repo.OrderRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;
    private final WebClient.Builder webClientBuilder;

    public OrderDTOResponse addOrder(OrderDTO orderDTO) throws OrderException {

        List<String> pids = orderDTO.getOrderLineItems()
                .stream()
                .map(OrderLineItemDTO::getProductId)
                .toList();

        ProductAvailabilityDTOResponse[] response = webClientBuilder.build().get().uri("lb://product-service/api/v1/product/availability",
                uriBuilder -> uriBuilder.queryParam("pid", pids).build())
                .retrieve()
                .bodyToMono(ProductAvailabilityDTOResponse[].class)
                .block();

        log.info("Availability check response:"+ Arrays.toString(response));
        if (null==response || response.length==0) throw new OrderException("Product not found. Please try again later.");


        Map<String, Integer> availabilityMap = new HashMap<>();

        Arrays.asList(response)
                .forEach(it->availabilityMap.put(it.getProductId(),it.getAvailableCount()));


        boolean allProductsInStock = orderDTO.getOrderLineItems().stream()
                .allMatch(orderLineItemDTO ->
                        orderLineItemDTO.getQuantity() <= availabilityMap.get(orderLineItemDTO.getProductId()));

        if (!allProductsInStock) throw new OrderException("Not enough inventory. Please try again later.");
        Order newlyCreatedOrder =
                orderRepo.save(Order.builder()
                        .orderById(orderDTO.getOrderById())
                        .orderByEmail(orderDTO.getOrderByEmail())
                        .orderByName(orderDTO.getOrderByName())
                        .orderDate(null == orderDTO.getOrderDate() ? new Date() : orderDTO.getOrderDate())
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
                .orderId(order.getId())
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
