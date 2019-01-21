package com.spring5.webflux.demo.services.impl;

import com.spring5.webflux.demo.models.City;
import com.spring5.webflux.demo.services.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@DisplayName("Spring boot 2 mockito2 Junit5 example")
public class CityServiceTest {

    @Mock
    private CityService cityService;

    private final Flux<City> MOCK_CITIES = Flux.just(
            City.builder().id("2")
                    .name("Probistip").build());
    private final String FIND_CITY = "Skopje";

    private final Mono<City> FINDED_CITY = Mono.just(City.builder()
            .id("1")
            .name("Skopje").build());

    @BeforeEach
    void setMockOutput() {
        MockitoAnnotations.initMocks(this);

        when(cityService.getAll()).thenReturn(MOCK_CITIES);
        when(cityService.getByName(FIND_CITY)).thenReturn(FINDED_CITY);
    }

    @Test
    public void getAllCities() {
        assertEquals(cityService.getAll(), MOCK_CITIES);
    }

    @Test
    public void getByName() {
        assertEquals(cityService.getByName(FIND_CITY), FINDED_CITY);
    }
}
