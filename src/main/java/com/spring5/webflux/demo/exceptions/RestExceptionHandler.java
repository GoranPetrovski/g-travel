package com.spring5.webflux.demo.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class RestExceptionHandler implements WebExceptionHandler {

    private ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {
        if (throwable instanceof PostNotFoundException) {
            serverWebExchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
            return serverWebExchange.getResponse().setComplete();
        }
        return Mono.error(throwable);
    }
}
