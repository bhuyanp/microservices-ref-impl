package com.example.microservice.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain getFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec
                        .pathMatchers("/eureka/**").permitAll()
                        .pathMatchers("/api/v1/*/**").permitAll()
                        .pathMatchers("/customer/**").permitAll()
                        .pathMatchers("/product/**").permitAll()
                        .pathMatchers("/order/**").permitAll()
                        .pathMatchers("/*.css").permitAll()
                        .pathMatchers("/*.js").permitAll()
                        .anyExchange().authenticated())
                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt)
                .build();
    }

}
