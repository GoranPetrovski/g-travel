package com.spring5.webflux.demo.web;

import com.spring5.webflux.demo.exceptions.BookNotFoundException;
import com.spring5.webflux.demo.models.Book;
import com.spring5.webflux.demo.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class BookHandler {
    @Autowired
    private BookService bookService;

    public Mono<ServerResponse> findById(ServerRequest request){
        String id = request.pathVariable("id");

        return ok()
                .contentType(APPLICATION_JSON)
                .body(bookService.findById(id), Book.class)
                .switchIfEmpty(Mono.error(new BookNotFoundException(id)));
                //.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Mono<ServerResponse> findAll(ServerRequest request){
        return ok()
                .contentType(APPLICATION_JSON)
                .body(bookService.findAll(), Book.class);
    }

    public Mono<ServerResponse> save(ServerRequest request){
        final Mono<Book> book = request.bodyToMono(Book.class);

        return ok()
                .contentType(APPLICATION_JSON)
                .body(fromPublisher(book.flatMap(bookService::save), Book.class));
    }

    public Mono<ServerResponse> delete(ServerRequest request){
        String id = request.pathVariable("id");
        return ok()
                .contentType(APPLICATION_JSON)
                .body(bookService.deleteById(id), Void.class);
    }
}
