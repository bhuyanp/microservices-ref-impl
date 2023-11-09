package com.example.microservice.order;

import com.example.microservice.order.dto.OrderDTO;
import com.example.microservice.order.dto.OrderDTOResponse;
import com.example.microservice.order.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
    public List<OrderDTOResponse> getCustomers() {
        return orderService.getAllOrders()
                .stream().map(hateosLinkFunction)
                .toList();
    }

    @PostMapping(path = URI, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTOResponse addCustomer(@RequestBody OrderDTO orderDTO) {
        return orderService.addOrder(orderDTO);
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
