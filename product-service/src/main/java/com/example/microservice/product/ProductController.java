package com.example.microservice.product;

import com.example.microservice.product.dto.ProductAvailabilityDTOResponse;
import com.example.microservice.product.dto.ProductDTO;
import com.example.microservice.product.dto.ProductDTOResponse;
import com.example.microservice.product.service.ProductService;
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
@Tag(name = "Product Service")
@RestController
@RequiredArgsConstructor
public class ProductController {

    public static final String URI="/api/v1/product";

    private final ProductService productService;



    @PostMapping(URI)
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTOResponse addProduct(@RequestBody ProductDTO productDTO) {
        return productService.addProduct(productDTO);
    }

    @GetMapping(path = URI, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTOResponse> getProducts() {
        return productService.getAllProducts()
                .stream()
                .map(hateosLinkFunction)
                .toList();
    }

    @GetMapping(path=URI+"/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ProductDTOResponse getProduct(@PathVariable String id) {
        log.info("Looking for product with id : {}",id);
        Optional<ProductDTOResponse> optionalProductDTO = productService.getProduct(id)
                .map(hateosLinkFunction);
        return optionalProductDTO.orElse(null);
    }


    @GetMapping(path=URI+"/availability", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ProductAvailabilityDTOResponse> getProductAvailability(@RequestParam List<String> pid) {
        List<ProductAvailabilityDTOResponse> result = productService.getProductAvailabilities(pid);
        log.info("getProductAvailability:{}",result);
        return result;
    }

    private final Function<ProductDTOResponse, ProductDTOResponse> hateosLinkFunction = it -> it.add(Link.of(URI+"/" + it.getId()));

}


