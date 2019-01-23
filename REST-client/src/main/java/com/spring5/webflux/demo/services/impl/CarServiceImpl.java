package com.spring5.webflux.demo.services.impl;

import com.spring5.webflux.demo.helpers.BaseId;
import com.spring5.webflux.demo.models.Car;
import com.spring5.webflux.demo.repositories.CarRepository;
import com.spring5.webflux.demo.repositories.UserRepository;
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
    public Mono<Car> create(String id, Car car) {
        car.setUser(new BaseId(id));
        return carRepository.save(car);
    }
}
