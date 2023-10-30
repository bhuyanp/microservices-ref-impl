package com.example.microservice.product;

import com.example.microservice.product.dto.ProductDTO;
import com.example.microservice.product.dto.ProductDTOResponse;
import com.example.microservice.product.service.ProductService;
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
public class ProductController {

    public static final String URI="/api/product";

    private final ProductService productService;

    @Value("${server.port}")
    private int serverPort;

    @GetMapping
    public String home() {
        return "Product Microservice";
    }

    @GetMapping(URI)
    public List<Link> getCustomer() {
        return List.of(Link.of(getBaseURL()+"/get", "Get All Products"));
    }

    @PostMapping(URI)
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTOResponse addCustomer(@RequestBody ProductDTO productDTO) {
        return productService.addProduct(productDTO);
    }

    @GetMapping(URI+"/get")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTOResponse> getCustomers() {
        return productService.getAllProducts()
                .stream()
                .map(hateosLinkFunction)
                .toList();
    }

    @GetMapping(URI+"/get/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTOResponse getCustomer(@PathVariable String id) {
        log.info("Looking for product with id : {}",id);
        Optional<ProductDTOResponse> optionalProductDTO = productService.getProduct(id)
                .map(hateosLinkFunction);
        return optionalProductDTO.orElse(null);
    }

    private final Function<ProductDTOResponse, ProductDTOResponse> hateosLinkFunction = it -> it.add(Link.of(getBaseURL()+"/get/" + it.getId()));



    private String getBaseURL(){
        return "http://localhost:"+serverPort+URI;
    }


}


