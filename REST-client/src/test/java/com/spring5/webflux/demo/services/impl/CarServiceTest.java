package com.spring5.webflux.demo.services.impl;

import com.spring5.webflux.demo.helpers.BaseId;
import com.spring5.webflux.demo.models.Car;
import com.spring5.webflux.demo.services.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class CarServiceTest {

    @Mock
    CarService carService;

    private final Flux<Car> MOCK_CARS = Flux.just(
            Car.builder().id("1")
                    .brand("Audi")
                    .model("A4")
                    .user(new BaseId("1")).build());

    private final Mono<Car> MOCK_CAR = Mono.just(
            Car.builder().id("1")
                    .brand("Audi")
                    .model("A4")
                    .user(new BaseId("1")).build());

    private final String USER_ID = "1";
    private final Car NEW_CAR = Car.builder().id("1")
            .brand("Audi")
            .model("A4").build();

    @BeforeEach
    void setMockOutput() {
        MockitoAnnotations.initMocks(this);
        when(carService.getAll()).thenReturn(MOCK_CARS);
        when(carService.create(USER_ID, NEW_CAR)).thenReturn(MOCK_CAR);
    }

    @Test
    public void getAllCars() {
        assertEquals(carService.getAll(), MOCK_CARS);
    }

    @Test
    public void createCar() {
        assertEquals(carService.create(USER_ID, NEW_CAR), MOCK_CAR);
    }
}