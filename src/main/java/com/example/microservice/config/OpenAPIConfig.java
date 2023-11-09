package com.example.microservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microservices SpringBoot")
                        .version("v1")
                        .description("""
                        This is reference microservices implementation using spring boot and spring cloud.
                        
                        Visit <a href="http://localhost:8761/">Eureka Server</a> to navigate to other services.
                        """)
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                        .contact(new Contact().name("Prasanta Bhuyan").email("prasanta.k.bhuyan@gmail.com"))
                );
    }
}
