package com.spring5.webflux.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

//TODO Elena disabled to avoid handler disambiguate
//@Configuration
public class PostRouter {

    @Autowired
    private PostHandler postHandler;

    @Bean
    public RouterFunction<ServerResponse> routePosts() {
        return RouterFunctions.
                route(GET("/posts").and(accept(MediaType.APPLICATION_JSON)), this.postHandler::getAll)
                .andRoute(GET("/posts/{id}").and(accept(MediaType.APPLICATION_JSON)), this.postHandler::findById);
    }
}
