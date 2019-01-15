package com.spring5.webflux.demo.controllers;

import com.spring5.webflux.demo.models.Car;
import com.spring5.webflux.demo.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("")
    public Flux<Car> getAll() {
        return carService.getAll();
    }

    @PostMapping("")
    public Mono<Car> create(@RequestBody @Valid Car car) {
        return carService.create(car);
    }
}
