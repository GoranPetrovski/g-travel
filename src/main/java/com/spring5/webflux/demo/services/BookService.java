package com.spring5.webflux.demo.services;

import com.spring5.webflux.demo.models.Book;
import org.springframework.format.number.money.MonetaryAmountFormatter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {
    Mono<Book> findById(String id);

    Flux<Book> findAll();

    Mono<Book> save(Book book);

    Mono<Void> deleteById(String id);
}
