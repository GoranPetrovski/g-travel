package com.spring5.webflux.demo.controllers;

import com.spring5.webflux.demo.GTravelApplication;
import com.spring5.webflux.demo.models.City;
import com.spring5.webflux.demo.repositories.CityRepository;
import com.spring5.webflux.demo.repositories.TravelRepository;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = GTravelApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TravelControllerTest {

    @Autowired
    WebTestClient client;

    @MockBean
    TravelRepository travelRepository;

    @MockBean
    CityRepository cityRepository;

    private final String user = "user";
    private final String password = "password";

    @Test
    public void createTravel_shouldBeOk() {

        //create city (fromLocation)
        City fromLocation = City.builder()
                .id("1")
                .name("Skopje").build();

        //create city (toDestination)
        City toDestination = City.builder()
                .id("2")
                .name("Probishtip").build();

        //create user

        //create car => assign to user id

        given(cityRepository.findById("2"))
                .willReturn(Mono.just(City.builder()
                        .id("2")
                        .name("Probishtip")
                        .build()));

        System.out.println("get all cities");
        System.out.println("Num cities: " + cityRepository.findById("2"));

    }


}