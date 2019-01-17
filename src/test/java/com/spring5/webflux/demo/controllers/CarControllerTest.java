package com.spring5.webflux.demo.controllers;

import com.spring5.webflux.demo.GTravelApplication;
import com.spring5.webflux.demo.helpers.BaseId;
import com.spring5.webflux.demo.models.Car;
import com.spring5.webflux.demo.models.Post;
import com.spring5.webflux.demo.repositories.CarRepository;
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
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.BDDMockito.given;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = GTravelApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("test")
public class CarControllerTest {

    @Autowired
    WebTestClient client;

    @MockBean
    private CarRepository carRepository;

    private final String user = "user";
    private final String password = "password";

    @Test
    public void getAll_ShouldBeOk() {
        given(carRepository.findAll()).willReturn(
                Flux.just(
                        Car.builder()
                                .id("1")
                                .user(new BaseId("1"))
                                .brand("Audi").build(),
                        Car.builder()
                                .id("2")
                                .user(new BaseId("2"))
                                .brand("Golf").build()));

        client.mutate().filter(basicAuthentication(user, password)).build()
                .get().uri("/cars").exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.[1].id").isEqualTo("2")
                .jsonPath("$.[1].brand").isEqualTo("Golf")
                .jsonPath("$.[1].user.id").isEqualTo("2");
    }

    @Test
    public void createCarByUser_ShouldBeOk() {
        Car car = Car.builder()
                .user(new BaseId("1"))
                .brand("Audi")
                .model("A4")
                .numOfSeats(5).build();

        given(carRepository.save(car)).willReturn(Mono.just(Car.builder()
                .id("1")
                .user(new BaseId("1"))
                .brand("Audi").build()));

        client.mutate().filter(basicAuthentication(user, password)).build()
                .post().uri("/cars/1/user")
                .body(BodyInserters.fromObject(car))
                .exchange()
                .expectStatus().isOk()
                .expectBody();
                //.jsonPath("$.model").isEqualTo("A4")
                //.jsonPath("$.user.id").isEqualTo("1");
    }

}