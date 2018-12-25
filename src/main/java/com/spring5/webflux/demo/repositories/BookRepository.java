package com.spring5.webflux.demo.repositories;

import com.spring5.webflux.demo.models.Book;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BookRepository extends ReactiveMongoRepository<Book, String> {
}
