package com.spring5.webflux.demo.bootstrap;

import com.spring5.webflux.demo.helpers.PostId;
import com.spring5.webflux.demo.models.*;
import com.spring5.webflux.demo.repositories.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@Log4j2
@Component
public class Bootstrap implements CommandLineRunner {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("RUNNNN");
        if (bookRepository.count().block() == 0) {
            this.booksInitialization();
        }
        if (postRepository.count().block() == 0) {
            this.postsInitialization();
        }
        this.userInitialization();
    }

    public void userInitialization() {
        userRepository
                .deleteAll()
                .thenMany(
                        Flux
                                .just("user", "admin")
                                .flatMap(
                                        username -> {
                                            List<String> roles = "user".equals(username)
                                                    ? Arrays.asList("ROLE_USER")
                                                    : Arrays.asList("ROLE_USER", "ROLE_ADMIN");

                                            User user = User.builder()
                                                    .roles(roles)
                                                    .username(username)
                                                    .password(passwordEncoder.encode("password"))
                                                    .email(username + "@example.com")
                                                    .build();
                                            return userRepository.save(user);
                                        }
                                )
                )
                .log()
                .subscribe(
                        null,
                        null,
                        () -> log.info("done users initialization...")
                );
    }

    public void postsInitialization() {
        Post post = postRepository.save(Post.builder()
                .title("The most beautiful country")
                .content("Country with ...").build()).block();

        commentRepository.save(Comment.builder()
                .content("Yeah! It's very realistic post")
                .post(new PostId(post.getId())).build()).block();
    }

    public void booksInitialization() {
        bookRepository.save(Book.builder()
                .author("Dan Brown")
                .title("Deception Point").build()).block();

        bookRepository.save(Book.builder()
                .author("Dan Brown")
                .title("Disgital Forests").build()).block();

        bookRepository.save(Book.builder()
                .author("Dan Brown")
                .title("Angels and Demons").build()).block();
    }

    public void cityInitialization() {
        cityRepository.save(City.builder()
                .name("Skopje").build()).block();

        cityRepository.save(City.builder()
                .name("Probishtip").build()).block();

        cityRepository.save(City.builder()
                .name("Veles").build()).block();

    }
}
