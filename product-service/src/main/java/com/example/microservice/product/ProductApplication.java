package com.example.microservice.product;

import com.example.microservice.product.dto.ProductDTO;
import com.example.microservice.product.repo.ProductRepo;
import com.example.microservice.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;

@Controller
@SpringBootApplication
@ComponentScan(basePackages = {
        //getting common open api config from root project
        "com.example.microservice.config",
        "com.example.microservice.product"
})
public class ProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class,args);
    }

    @Autowired
    ProductRepo productRepo;
    @Autowired
    ProductService productService;

    @GetMapping(path = { "/", "/actuator/info" })
    public String home() {
        return "redirect:/swagger-ui.html";
    }

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

            System.out.println("Eureka: http://localhost:8761/");
            System.out.println("API Gateway: http://localhost:8080/api/v1/product");
        };
    }

}
