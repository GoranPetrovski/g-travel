package com.spring5.webflux.demo.controllers;

import com.spring5.webflux.demo.models.Travel;
import com.spring5.webflux.demo.services.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/travels")
public class TravelController {

    @Autowired
    private TravelService travelService;

    @GetMapping("")
    public Flux<Travel> getAll() {
        return travelService.all();
    }

    @PostMapping("")
    public Mono<Travel> create(@RequestBody @Valid Travel travel) {
        return travelService.create(travel);
    }

    @GetMapping("/{id}")
    public Mono<Travel> getById(@PathVariable("id") String id) {
        return travelService.getById(id);
    }

    @GetMapping("/{type}")
    public Flux<Travel> getByType(@PathVariable("type") Travel.Type type) {
        return travelService.getByType(type);
    }
}
