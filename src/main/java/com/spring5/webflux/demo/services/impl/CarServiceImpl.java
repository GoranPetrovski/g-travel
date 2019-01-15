package com.spring5.webflux.demo.services.impl;

import com.spring5.webflux.demo.models.Car;
import com.spring5.webflux.demo.repositories.CarRepository;
import com.spring5.webflux.demo.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Override
    public Flux<Car> getAll() {
        return carRepository.findAll();
    }

    @Override
    public Mono<Car> create(Car car) {
        return carRepository.save(car);
    }
}
