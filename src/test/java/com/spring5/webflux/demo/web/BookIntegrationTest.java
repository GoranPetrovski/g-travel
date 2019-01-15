package com.spring5.webflux.demo.web;

import com.spring5.webflux.demo.GTravelApplication;
import com.spring5.webflux.demo.models.Book;
import com.spring5.webflux.demo.repositories.BookRepository;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = GTravelApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookIntegrationTest {

    @Autowired
    private BookRouter bookRouter;

    @MockBean
    private BookRepository bookRepository;

    @Test
    public void givenBookId() {
        WebTestClient client = WebTestClient
                .bindToRouterFunction(bookRouter.getBookById())
                .build();

        Book book = new Book();
        book.setTitle("Rich dad, poor dad");
        book.setAuthor("unkown");

        given(bookRepository.findById("1")).willReturn(Mono.just(book));
        client.get()
                .uri("/fbook/1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Book.class).isEqualTo(book);

    }

}