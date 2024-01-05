package com.example.microservice.order;

import com.example.microservice.order.dto.OrderDTO;
import com.example.microservice.order.dto.OrderDTOResponse;
import com.example.microservice.order.exception.OrderException;
import com.example.microservice.order.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name="Order Service")
public class OrderController {

    private final OrderService orderService;

    public static final String URI="/api/v1/order";


    @GetMapping(path = URI, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDTOResponse> getOrders() {
        return orderService.getAllOrders()
                .stream().map(hateosLinkFunction)
                .toList();
    }

    @PostMapping(path = URI, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name="product", fallbackMethod = "placeOrderFallback")
    @Retry(name="product")//to test retry, comment out circuit breaker annotation
    @TimeLimiter(name = "product", fallbackMethod = "placeOrderTimeoutFallback")
    public CompletableFuture<String> placeOrder(@RequestBody OrderDTO orderDTO) {
        log.info("inside place order");
        return CompletableFuture.supplyAsync(()->{
            OrderDTOResponse orderDTOResponse;
            try {
                orderDTOResponse = orderService.addOrder(orderDTO);
            } catch (OrderException e) {
                return e.getMessage();
            }
            return "Order placed successfully. Your order id is "+orderDTOResponse.getOrderId()+".";
        });
    }

    public CompletableFuture<String> placeOrderFallback(OrderDTO orderDTO, RuntimeException rte) {
        log.error("Inside placeOrderFallback.Message:{}",rte.getMessage());
        return CompletableFuture.supplyAsync(()->"Unable to place your order. Please try again later.");
    }

    public CompletableFuture<String> placeOrderTimeoutFallback(OrderDTO orderDTO, RuntimeException rte) {
        return CompletableFuture.supplyAsync(()->"Request timed out. Please try again later.");
    }

    @GetMapping(path = URI+"/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public OrderDTOResponse getOrder(@PathVariable String id) {
        log.info("Looking for order with id : {}",id);
        Optional<OrderDTOResponse> optionalProductDTO = orderService.getOrder(id)
                .map(hateosLinkFunction);
        return optionalProductDTO.orElse(null);
    }

    private final Function<OrderDTOResponse, OrderDTOResponse> hateosLinkFunction = it -> it.add(Link.of(URI+"/" + it.getOrderId()));

}
