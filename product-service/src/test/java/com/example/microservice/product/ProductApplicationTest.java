package com.example.microservice.product;

import com.example.microservice.product.dto.ProductDTO;
import com.example.microservice.product.model.Product;
import com.example.microservice.product.repo.ProductRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;

import static com.example.microservice.product.ProductController.SERVICE_URI;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class ProductApplicationTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:5.0.22"));

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ProductRepo productRepo;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
        dynamicPropertyRegistry.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);
    }



    @Test
    void contextLoads() {
    }

    @BeforeEach
    void reset() {
        productRepo.deleteAll();
    }



    @Test
    void shouldCreateProduct() throws Exception {
        String productStr = objectMapper.writeValueAsString(ProductDTO.builder()
                            .title("test product")
                            .description("description")
                            .price(BigDecimal.valueOf(5)).build());
        mockMvc.perform(MockMvcRequestBuilders.post(SERVICE_URI)
                    .contentType("application/json")
                    .content(productStr))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("test product"));
        assertEquals(1, productRepo.findAll().size());
    }

    @Test
    void shouldListProducts() throws Exception {
        productRepo.save(Product.builder()
                .title("PA")
                .description("descriptionA")
                .price(BigDecimal.valueOf(5))
                .build());

        productRepo.save(Product.builder()
                .title("PB")
                .description("descriptionB")
                .price(BigDecimal.valueOf(10))
                .build());



        mockMvc.perform(MockMvcRequestBuilders.get(SERVICE_URI)
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}

