package com.example.microservice.order;

import com.example.microservice.order.dto.OrderDTO;
import com.example.microservice.order.dto.OrderDTOResponse;
import com.example.microservice.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    public static final String URI="/api/order";


    @Value("${server.port}")
    private int serverPort;

    @GetMapping
    public String home() {
        return "Order Microservice";
    }


    @GetMapping(URI)
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDTOResponse> getCustomers() {
        return orderService.getAllOrders()
                .stream().map(hateosLinkFunction)
                .toList();
    }

    @PostMapping(URI)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTOResponse addCustomer(@RequestBody OrderDTO orderDTO) {
        return orderService.addOrder(orderDTO);
    }



    @GetMapping(URI+"/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTOResponse getOrder(@PathVariable String id) {
        log.info("Looking for order with id : {}",id);
        Optional<OrderDTOResponse> optionalProductDTO = orderService.getOrder(id)
                .map(hateosLinkFunction);
        return optionalProductDTO.orElse(null);
    }

    private final Function<OrderDTOResponse, OrderDTOResponse> hateosLinkFunction = it -> it.add(Link.of(URI+"/" + it.getOrderId()));

}
