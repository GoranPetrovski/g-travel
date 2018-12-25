package com.spring5.webflux.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class BookRouter {

    @Autowired
    private BookHandler bookHandler;

    @Bean
    public RouterFunction<ServerResponse> route() {
        return RouterFunctions
                .route(GET("/fbook").and(accept(MediaType.APPLICATION_JSON)), this.bookHandler::findAll)
                .andRoute(GET("/fbook/{id}").and(accept(MediaType.APPLICATION_STREAM_JSON)), this.bookHandler::findById)
                .andRoute(POST("/fbook").and(accept(MediaType.APPLICATION_JSON)), this.bookHandler::save)
                .andRoute(DELETE("/fbook/{id}").and(accept(MediaType.APPLICATION_JSON)), this.bookHandler::delete);
    }

    @Bean
    public RouterFunction<ServerResponse> getBookById() {
        return RouterFunctions.route(GET("/fbook/{id}").and(accept(MediaType.APPLICATION_STREAM_JSON)), this.bookHandler:: findById);
    }
}
