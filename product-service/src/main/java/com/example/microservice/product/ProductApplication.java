package com.example.microservice.product;

import com.example.microservice.product.dto.ProductDTO;
import com.example.microservice.product.repo.ProductRepo;
import com.example.microservice.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class ProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class,args);
    }

    @Autowired
    ProductRepo productRepo;
    @Autowired
    ProductService productService;

    @Bean
    public CommandLineRunner commandLineRunner(){
        return args -> {
            productRepo.deleteAll();

            System.out.println(productService
                    .addProduct(new ProductDTO("Product A","description A", BigDecimal.valueOf(100),100)));
            System.out.println(productService
                    .addProduct(new ProductDTO("Product B","description B", BigDecimal.valueOf(200),200)));
            System.out.println(productService
                    .addProduct(new ProductDTO("Product C","description C", BigDecimal.valueOf(300),300)));

            System.out.println(productRepo.findAll());
        };
    }

}
