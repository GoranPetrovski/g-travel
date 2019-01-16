package com.spring5.webflux.demo.controllers;

import com.spring5.webflux.demo.GTravelApplication;
import com.spring5.webflux.demo.models.City;
import com.spring5.webflux.demo.repositories.CityRepository;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.BDDMockito.given;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = GTravelApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("test")
public class CityControllerTest {

    @Autowired
    WebTestClient client;

    @MockBean
    private CityRepository cityRepository;

    private final String user = "user";
    private final String password = "password";

    @Test
    public void getAll_ShouldBeOk() {
        given(cityRepository.findAll()).willReturn(
                Flux.just(
                        City.builder()
                                .id("1")
                                .name("Skopje").build(),
                        City.builder()
                                .id("2")
                                .name("Probishtip").build()));

        client.mutate().filter(basicAuthentication(user, password)).build()
                .get().uri("/cities").exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.[1].id").isEqualTo("2")
                .jsonPath("$.[1].name").isEqualTo("Probishtip");
    }

    @Test
    public void findCityByName_ShouldBeOk() {

        given(cityRepository.findByName("Skopje")).willReturn(Mono.just(City.builder()
                .id("1")
                .name("Skopje").build()));

        client.mutate().filter(basicAuthentication(user, password)).build()
                .get()
                .uri("/cities/Skopje").exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("1")
                .jsonPath("$.name").isEqualTo("Skopje");
    }

}