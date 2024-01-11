package com.example.microservice.product;

import com.example.microservice.product.dto.ProductDTO;
import com.example.microservice.product.repo.ProductRepo;
import com.example.microservice.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;

@Controller
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = {
        //getting common open api config from root project
        "com.example.microservice.openapidoc",
        "com.example.microservice.product"
})
@EnableDiscoveryClient
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

            int port = "docker".equals(profile)?8080:9090;
            System.out.printf("Eureka: http://localhost:%s/eureka/web%n", port);
            System.out.printf("Swagger UI(wait 30s): http://localhost:%s/product%n", port);
        };
    }

    @Value("${spring.profiles.active:unknown}")
    String profile;

}
