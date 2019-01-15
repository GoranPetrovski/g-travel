package com.spring5.webflux.demo.bootstrap;

import com.spring5.webflux.demo.helpers.BaseId;
import com.spring5.webflux.demo.helpers.PostId;
import com.spring5.webflux.demo.models.*;
import com.spring5.webflux.demo.repositories.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Log4j2
@Component
@Profile("!test")
public class Bootstrap implements CommandLineRunner {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private TravelRepository travelRepository;

    @Autowired
    private TravelOfferRepository travelOfferRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("RUN ...");
        if (isEmpty(postRepository.count().block())) {
            this.postsInitialization();
        }
        if (isEmpty(cityRepository.count().block())) {
            this.cityInitialization();
        }
        if (isEmpty(travelRepository.count().block())) {
            this.travelInitialization();
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

    public void cityInitialization() {
        cityRepository.save(City.builder()
                .name("Skopje").build()).block();

        cityRepository.save(City.builder()
                .name("Probishtip").build()).block();

        cityRepository.save(City.builder()
                .name("Veles").build()).block();
    }

    public void travelInitialization() {
        String fromLocationId = cityRepository.findByName("Skopje").block().getId();
        String toDestinationId = cityRepository.findByName("Probishtip").block().getId();
        String userId = userRepository.findByUsername("user").block().getId();


        Mono<Travel> travel = travelRepository.save(Travel.builder()
                .fromLocation(new BaseId(fromLocationId))
                .toDestination(new BaseId(toDestinationId))
                .date(LocalDate.now()).user(new BaseId(userId))
                .type(Travel.Type.DRIVER).build());

        Mono<Car> car = carRepository.save(Car.builder()
                .brand("VW")
                .model("Golf V")
                .user(new BaseId(userId))
                .numOfSeats(5).build());

        travelOfferRepository.save(TravelOffer.builder()
                .travel(new BaseId(travel.block().getId()))
                .car(new BaseId(car.block().getId()))
                .freeSeats(3)
                .build()).block();

    }

    private Boolean isEmpty(Long num) {
        if (num == 0) {
            return true;
        }
        return false;
    }

}
