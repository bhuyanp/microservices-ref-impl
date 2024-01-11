package com.example.microservice.product.repo;

import com.example.microservice.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends MongoRepository<Product, String> {

    public List<Product> findByIdIn(List<String> id);

}
