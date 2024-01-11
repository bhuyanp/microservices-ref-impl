package com.example.microservice.order;

import com.example.microservice.order.dto.OrderRequest;
import com.example.microservice.order.dto.OrderDTOResponse;
import com.example.microservice.order.exception.OrderException;
import com.example.microservice.order.service.OrderService;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @PostMapping(path = URI)
    @ResponseStatus(HttpStatus.CREATED)
    //@CircuitBreaker(name="product", fallbackMethod = "placeOrderFallback")//to test uncomment one at a time
    //@Retry(name="product", fallbackMethod = "placeOrderFallback")//to test uncomment one at a time
    //@TimeLimiter(name = "product", fallbackMethod = "placeOrderTimeoutFallback")//to test uncomment one at a time
    public CompletableFuture<String> placeOrder(@Valid @RequestBody OrderRequest orderDTO) {
        log.info("Inside place order");
        return CompletableFuture.supplyAsync(()->{
            OrderDTOResponse orderDTOResponse;
            try {
                orderDTOResponse = orderService.placeOrder(orderDTO);
            } catch (OrderException e) {
                return e.getMessage();
            }
            return "Order placed successfully. Your order id is "+orderDTOResponse.getOrderId()+".";
        });
    }

    public CompletableFuture<String> placeOrderFallback(OrderRequest orderDTO, RuntimeException rte) {
        log.error("Inside placeOrderFallback.Message:{}",rte.getMessage());
        return CompletableFuture.supplyAsync(()->"Unable to place your order. Please try again later.");
    }

    public CompletableFuture<String> placeOrderTimeoutFallback(OrderRequest orderDTO, RuntimeException rte) {
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


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.warn("Order validation messages: {}",errors);
        return errors;
    }

    private final Function<OrderDTOResponse, OrderDTOResponse> hateosLinkFunction = it -> it.add(Link.of(URI+"/" + it.getOrderId()));

}
