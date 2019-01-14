package com.spring5.webflux.demo.repositories;

import com.spring5.webflux.demo.models.Car;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CarRepository extends ReactiveMongoRepository<Car, String> {
}
