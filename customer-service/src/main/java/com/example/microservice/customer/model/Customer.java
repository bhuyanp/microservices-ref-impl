package com.example.microservice.customer.model;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Table(name = "CUSTOMER")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Nonnull
    @Column(name="first_name")
    private String firstName;

    @Nonnull
    @Column(name="last_name")
    private String lastName;

    @Nonnull
    private String email;

}
