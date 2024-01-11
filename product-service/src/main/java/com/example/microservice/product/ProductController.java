package com.example.microservice.product;

import com.example.microservice.product.dto.ProductAvailabilityDTOResponse;
import com.example.microservice.product.dto.ProductDTO;
import com.example.microservice.product.dto.ProductDTOResponse;
import com.example.microservice.product.exception.ProductNotFoundException;
import com.example.microservice.product.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;

@Slf4j
@Tag(name = "Product Service")
@RestController
@RequiredArgsConstructor
public class ProductController {

    public static final String SERVICE_URI="/api/v1/product";

    private final ProductService productService;



    @PostMapping(SERVICE_URI)
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTOResponse addProduct(@RequestBody ProductDTO productDTO) {
        return productService.addProduct(productDTO);
    }

    @GetMapping(path = SERVICE_URI, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ProductDTOResponse> getProducts() {
        return productService.getAllProducts()
                .stream()
                .map(hateosLinkFunction)
                .toList();
    }

    @GetMapping(path=SERVICE_URI+"/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductDTOResponse> getProduct(@PathVariable String id) {
        log.info("Looking for product with id : {}",id);
        try {
            ProductDTOResponse productDTO = productService.getProduct(id);
            return new ResponseEntity<ProductDTOResponse>(productDTO, HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<ProductDTOResponse>(HttpStatus.NO_CONTENT);
        }
    }


    @GetMapping(path=SERVICE_URI+"/availability", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ProductAvailabilityDTOResponse> getProductAvailability(@RequestParam List<String> pid) {

        log.info("Checking the availability of "+pid);

        //Uncomment thread.sleep to test Timeout scenario from order service
        /*try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        List<ProductAvailabilityDTOResponse> result = productService.getProductAvailabilities(pid);
        log.info("getProductAvailability:{}",result);
        return result;
    }

    private final Function<ProductDTOResponse, ProductDTOResponse> hateosLinkFunction = it -> it.add(Link.of(SERVICE_URI+"/" + it.getId()));

}


