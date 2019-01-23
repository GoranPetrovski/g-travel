package com.spring5.webflux.demo.controllers;

import com.spring5.webflux.demo.GTravelApplication;
import com.spring5.webflux.demo.helpers.BaseId;
import com.spring5.webflux.demo.models.*;
import com.spring5.webflux.demo.repositories.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = GTravelApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("test")
public class TravelControllerTest {

    @Autowired
    WebTestClient client;

    @MockBean
    TravelRepository travelRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    CityRepository cityRepository;

    @MockBean
    CarRepository carRepository;

    @MockBean
    TravelOfferRepository travelOfferRepository;

    private final String user = "user";
    private final String password = "password";

    @Test
    public void createTravel_shouldBeOk() {
        given(cityRepository.findByName("Probishtip"))
                .willReturn(Mono.just(City.builder().id("2")
                        .name("Probishtip").build()));

        given(cityRepository.findByName("Skopje"))
                .willReturn(Mono.just(City.builder().id("1")
                        .name("Skopje").build()));

        given(userRepository.findByUsername("user"))
                .willReturn(Mono.just(User.builder().id("1")
                        .username("user").active(true).build()));

        String locationId = cityRepository.findByName("Probishtip").block().getId();
        String destinationId = cityRepository.findByName("Skopje").block().getId();
        String userId = userRepository.findByUsername("user").block().getId();

        Travel createNewTravel = Travel.builder()
                .fromLocation(new BaseId(locationId))
                .toDestination(new BaseId(destinationId))
                .user(new BaseId(userId))
                .type(Travel.Type.DRIVER)
                .date(LocalDate.now().plusDays(2)).build();

        given(travelRepository.save(createNewTravel))
                .willReturn(Mono.just(Travel.builder().id("1").build()));

        Mono<Travel> travelPr_SkPlus2 = travelRepository.save(createNewTravel);

        Car createNewCar = Car.builder()
                .id("1")
                .user(new BaseId(userId))
                .brand("Audi")
                .model("A6")
                .numOfSeats(5).build();

        given(carRepository.save(createNewCar))
                .willReturn(Mono.just(Car.builder().id("1").brand("Audi").build()));

        Mono<Car> car = carRepository.save(createNewCar);

        travelOfferRepository.save(TravelOffer.builder()
                .travel(new BaseId(travelPr_SkPlus2.block().getId()))
                .car(new BaseId(car.block().getId()))
                .build());
    }
}