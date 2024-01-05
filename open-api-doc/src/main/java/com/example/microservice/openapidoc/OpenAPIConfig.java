package com.example.microservice.openapidoc;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(
        type = SecuritySchemeType.OAUTH2,
        name = "Oauth2Password",
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER,
        flows = @OAuthFlows(
                password = @OAuthFlow(
                        tokenUrl = "http://localhost:8084/realms/springboot-microservices/protocol/openid-connect/token"
                )
        )
)
public class OpenAPIConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Microservices SpringBoot")
                        .version("v1")
                        .description("""
                                This is reference microservices implementation using spring boot and spring cloud.
                                                        
                                Visit <a href="http://localhost:8080/eureka/web">Eureka Server</a> to navigate to other services.
                                """)
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                        .contact(new Contact().name("Prasanta Bhuyan").email("prasanta.k.bhuyan@gmail.com"))
                );
//                .servers(List.of(
//                        new Server()
//                                .url("http://localhost:8080")
//                                .description(""))
//                );
    }
}
