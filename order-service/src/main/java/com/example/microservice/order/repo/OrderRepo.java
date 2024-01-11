package com.example.microservice.order.repo;

import com.example.microservice.order.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends MongoRepository<Order, String> {

}
