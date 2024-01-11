package com.example.microservice.product.service;

import com.example.microservice.product.dto.ProductAvailabilityDTOResponse;
import com.example.microservice.product.dto.ProductDTO;
import com.example.microservice.product.dto.ProductDTOResponse;
import com.example.microservice.product.exception.ProductNotFoundException;
import com.example.microservice.product.model.Product;
import com.example.microservice.product.repo.ProductRepo;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Observed(name="ProductService")
public class ProductService {
    private final ProductRepo productRepo;

    public ProductDTOResponse addProduct(ProductDTO productDTO) {
        Product newlyCreatedProduct =
                productRepo.save(getProduct(productDTO));
        return getProductDTO(newlyCreatedProduct);
    }

    public ProductDTOResponse getProduct(String id) throws ProductNotFoundException{
        return productRepo.findById(id).map(this::getProductDTO).orElseThrow(ProductNotFoundException::new);
    }

    public List<ProductDTOResponse> getAllProducts() {
        return productRepo.findAll().stream().map(this::getProductDTO).toList();
    }


    public List<ProductAvailabilityDTOResponse> getProductAvailabilities(List<String> pids) {
        log.info("GetProductAvailabilities for "+pids);
        return productRepo.findByIdIn(pids)
                .stream()
                .map(product->ProductAvailabilityDTOResponse.builder()
                    .productId(product.getId())
                    .availableCount(product.getAvailableInventory())
                    .build())
                .toList();
    }


    private ProductDTOResponse getProductDTO(Product product){
        return ProductDTOResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .available(product.getAvailableInventory()>0)
                .build();
    }

    private Product getProduct(ProductDTO productDTO){
        return Product.builder()
                .title(productDTO.getTitle())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .availableInventory(productDTO.getAvailableInventory())
                .build();
    }

}
