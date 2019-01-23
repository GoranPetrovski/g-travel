package com.spring5.webflux.demo.controllers;


import com.spring5.webflux.demo.models.City;
import com.spring5.webflux.demo.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping("")
    public Flux<City> getAll() {
        return cityService.getAll();
    }

    @GetMapping("/{name}")
    public Mono<City> getByName(@PathVariable("name") String name) {
        return cityService.getByName(name);
    }
}
