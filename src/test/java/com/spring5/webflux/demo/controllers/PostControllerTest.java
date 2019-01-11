package com.spring5.webflux.demo.controllers;

import com.spring5.webflux.demo.BookserviceApplication;
import com.spring5.webflux.demo.helpers.PostId;
import com.spring5.webflux.demo.models.Comment;
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
import reactor.core.publisher.Mono;

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

    @Test
    public void getPostByNonExistedId_shouldReturn404() {
        given(postRepository.findById("1"))
                .willReturn(Mono.empty());

        client.get().uri("/posts/1").exchange()
                .expectStatus().isNotFound();

        verify(postRepository, times(1)).findById(anyString());
        verifyNoMoreInteractions(postRepository);
    }

    @Test
    public void updatePost_shouldBeOk() {
        Post post = Post.builder()
                .id("1")
                .title("my first post")
                .content("content of my first post")
                .createdDate(LocalDateTime.now()).build();

        given(postRepository.findById("1"))
                .willReturn(Mono.just(post));

        post.setTitle("updated title");
        post.setContent("updated content");

        given(postRepository.save(post))
                .willReturn(Mono.just(Post.builder()
                        .id("1")
                        .title("updated title")
                        .content("updated content")
                        .createdDate(LocalDateTime.now()).build()));

        client.mutate().filter(basicAuthentication(user, password)).build()
                .put().uri("/posts/1").body(BodyInserters.fromObject(post))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.title").isEqualTo("updated title")
                .jsonPath("$.id").isEqualTo("1")
                .jsonPath("$.content").isEqualTo("updated content")
                .jsonPath("$.createdDate").isNotEmpty();

        verify(postRepository, times(1)).findById(anyString());
        verify(postRepository, times(1)).save(any(Post.class));
        verifyNoMoreInteractions(postRepository);
    }

    @Test
    public void createPost_shouldBeOk() {
        Post post = Post.builder()
                .title("my first post")
                .content("content of my first post").build();

        given(postRepository.save(post))
                .willReturn(Mono.just(Post.builder()
                        .id("1")
                        .title("my first post")
                        .content("content of my first post")
                        .createdDate(LocalDateTime.now()).build()));

        client.mutate().filter(basicAuthentication(user, password)).build()
                .post().uri("/posts").body(BodyInserters.fromObject(post))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.title").isEqualTo("my first post")
                .jsonPath("$.id").isEqualTo("1")
                .jsonPath("$.content").isEqualTo("content of my first post")
                .jsonPath("$.createdDate").isNotEmpty();

        verify(postRepository, times(1)).save(any(Post.class));
        verifyNoMoreInteractions(postRepository);
    }

    @Test
    public void deletePost_shouldBeOk() {
        Post post = Post.builder()
                .id("1")
                .title("my first post")
                .content("content of my first post")
                .createdDate(LocalDateTime.now()).build();

        given(postRepository.findById("1"))
                .willReturn(Mono.just(post));

        given(postRepository.delete(post))
                .willReturn(Mono.empty());

        client.mutate().filter(basicAuthentication(user, password)).build()
                .delete().uri("/posts/1")
                .exchange()
                .expectStatus().isNoContent();

        verify(postRepository, times(1)).findById(anyString());
        verify(postRepository, times(1)).delete(any(Post.class));
        verifyNoMoreInteractions(postRepository);
    }

    //TODO consult with Eli
//    @Test
//    public void getCommentsByPostId_shouldBeOk() {
//        given(commentRepository.findByPost(new PostId("1")))
//                .willReturn(Flux.just(Comment.builder()
//                        .id("comment-id-1")
//                        .post(new PostId("1"))
//                        .content("comment of my first post").build()));
//
//        client.mutate().filter(basicAuthentication(user, password)).build()
//                .get().uri("/posts/1/comments").exchange()
//                .expectStatus().isOk()
//                .expectBody()
//                .jsonPath("$[0].id").isEqualTo("comment-id-1")
//                .jsonPath("$[0].content").isEqualTo("comment of my first post");
//
//        verify(commentRepository, times(1)).findByPost(any(PostId.class));
//        verifyNoMoreInteractions(commentRepository);
//
//    }

    @Test
    public void createCommentOfPost_shouldBeOk() {

        given(commentRepository.save(any(Comment.class)))
                .willReturn(Mono.just(Comment.builder()
                        .id("comment-id-1")
                        .post(PostId.builder().id("1").build())
                        .content("content of my first post")
                        .createdDate(LocalDateTime.now()).build()));

        Comment form = Comment.builder().content("comment of my first post").build();
        client.mutate().filter(basicAuthentication(user, password)).build()
                .post().uri("/posts/1/comments").body(BodyInserters.fromObject(form))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("comment-id-1")
                .jsonPath("$.content").isEqualTo("content of my first post")
                .jsonPath("$.createdDate").isNotEmpty();

        verify(commentRepository, times(1)).save(any(Comment.class));
        verifyNoMoreInteractions(commentRepository);
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