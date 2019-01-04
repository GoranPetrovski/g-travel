package com.spring5.webflux.demo.bootstrap;

import com.spring5.webflux.demo.models.Book;
import com.spring5.webflux.demo.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("RUNNNN");
        if (bookRepository.count().block() == 0) {
            bookRepository.save(Book.builder()
                    .author("Dan Brown")
                    .title("Deception Point").build()).block();

            System.out.println("Loaded books " + bookRepository.count().block());

            bookRepository.save(Book.builder()
                    .author("Dan Brown")
                    .title("Disgital Forests=").build()).block();

            bookRepository.save(Book.builder()
                    .author("Dan Brown")
                    .title("Angels and Demons").build()).block();

            System.out.println("Loaded books " + bookRepository.count().block());
        }

    }
}
