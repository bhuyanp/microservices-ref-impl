package com.example.microservice.product.service;

import com.example.microservice.product.dto.ProductDTO;
import com.example.microservice.product.dto.ProductDTOResponse;
import com.example.microservice.product.model.Product;
import com.example.microservice.product.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;

    public ProductDTOResponse addProduct(ProductDTO productDTO) {
        Product newlyCreatedProduct =
                productRepo.save(new Product(productDTO.getTitle(), productDTO.getDescription(),productDTO.getPrice()));
        return getProductDTO(newlyCreatedProduct);
    }

    public Optional<ProductDTOResponse> getProduct(String id) {
        return productRepo.findById(id).map(this::getProductDTO);
    }

    public List<ProductDTOResponse> getAllProducts() {
        return productRepo.findAll().stream().map(this::getProductDTO).toList();
    }


    private ProductDTOResponse getProductDTO(Product product){
        return ProductDTOResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

}
