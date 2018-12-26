package com.spring5.webflux.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ProfileRouter {

    @Autowired
    private ProfileHandler handler;

    @Bean
    RouterFunction<ServerResponse> routes() {
        return route(GET("/profiles"), handler::all)
                .andRoute(GET("/profiles/{id}"), handler::getById)
                .andRoute(DELETE("/profiles/{id}"), handler::deleteById)
                .andRoute(PUT("/profiles/{id}"), handler::updateById);
    }

    @Bean
    public RouterFunction<ServerResponse> getProfiles() {
        return RouterFunctions.route(GET("/profiles").and(accept(MediaType.APPLICATION_JSON_UTF8)), handler::all);
    }
}
