package com.spring5.webflux.demo.controllers;

import com.spring5.webflux.demo.BookserviceApplication;
import com.spring5.webflux.demo.models.Post;
import com.spring5.webflux.demo.repositories.CommentRepository;
import com.spring5.webflux.demo.repositories.PostRepository;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BookserviceApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PostControllerTest {

    @Autowired
    WebTestClient client;

    @MockBean
    PostRepository postRepository;

    @MockBean
    CommentRepository commentRepository;

    private final String user = "user";
    private final String password = "password";

    @Test
    public void createPostWithoutAuthentication_shouldReturn401() {
        client
                .post()
                .uri("/posts")
                .body(BodyInserters.fromObject(Post.builder().title("Post test").content("content of post test").build()))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void getAllPostsWithAuthentication_ShouldBeOk() {
        given(postRepository.findAll())
                .willReturn(Flux.empty());
        client
                .get()
                .uri("/posts")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void getAllPosts_shouldBeOk() {
        given(postRepository.findAll())
                .willReturn(Flux.just(
                        Post.builder()
                                .id("1")
                                .title("my new post")
                                .content("content of my new post")
                                .createdDate(LocalDateTime.now())
                                .status(Post.Status.PUBLISHED).build()
                ));

        client
                //basic authenitication with username and password
                .mutate().filter(basicAuthentication(user, password)).build()
                .get().uri("/posts").exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].title").isEqualTo("my new post")
                .jsonPath("$[0].id").isEqualTo("1")
                .jsonPath("$[0].content").isEqualTo("content of my new post");

        verify(postRepository, times(1)).findAll();
        verifyNoMoreInteractions(postRepository);
    }

    //DOTO consult with Eli
//    @Test
//    public void updateNoneExistedPostWithUserRole_shouldReturn404() {
//        client
//                .mutate().filter(basicAuthentication("user", "password")).build()
//                .get()
//                .uri("/posts/4")
//                .exchange()
//                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
//    }

    @Test
    public void getAllPostsByKeyword_shouldBeOk() {

        List<Post> data = IntStream.range(1, 16)//15 posts will be created.
                .mapToObj(n -> this.buildPost(String.valueOf(n)))
                .collect(Collectors.toList());

        given(postRepository.findAll())
                .willReturn(Flux.fromIterable(data));

        client.mutate().filter(basicAuthentication(user, password)).build()
                .get().uri("/posts").exchange()
                .expectStatus().isOk()
                .expectBodyList(Post.class).hasSize(10);

        client.mutate().filter(basicAuthentication(user, password)).build()
                .get().uri("/posts?page={page}", 1).exchange()
                .expectStatus().isOk()
                .expectBodyList(Post.class).hasSize(5);

        client.mutate().filter(basicAuthentication(user, password)).build()
                .get().uri("/posts/count").exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.count").isEqualTo(15);

        client.mutate().filter(basicAuthentication(user, password)).build()
                .get().uri(uriBuilder -> uriBuilder
                .path("/posts/count")
                .queryParam("q", "2")
                .build()
        )
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.count").isEqualTo(2);

        verify(postRepository, times(4)).findAll();
        verifyNoMoreInteractions(postRepository);

    }

    private Post buildPost(String id) {
        return Post.builder()
                .id(id)
                .title("my " + id + " first post")
                .content("content of my " + id + " first post")
                .status(Post.Status.PUBLISHED)
                .createdDate(LocalDateTime.now())
                .build();
    }
}