package com.spring5.webflux.demo;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class ReactorSnippetsTest {

    private static List<String> words = Arrays.asList("the", "most", "beautiful", "girl");

    @Test
    public void simpleCreation() {
        Flux<String> fewWords = Flux.just("Hello", "World");
        Flux<String> manyWords = Flux.fromIterable(words);
        fewWords.subscribe(System.out::println);
        System.out.println();
        manyWords.subscribe(System.out::println);
    }

    @Test
    public void findingMissingLetter() {

        Flux<String> manyLetters = Flux
                .fromIterable(words)
                .flatMap(word -> Flux.fromArray(word.split("")))
                .distinct()
                .sort()
                .zipWith(Flux.range(1, Integer.MAX_VALUE), (string, count) -> String.format("%2d. %s", count, string));

        manyLetters.subscribe(System.out::println);
    }

    @Test
    public void shortCircuit() {
        Flux<String> helloPauseWorld = Mono.just("Hello")
                .concatWith(Mono.just("world")
                        .delaySubscription(Duration.ofMillis(500)));

        helloPauseWorld.subscribe(System.out::println);
    }

    @Test
    public void firstEmitting() {
        Mono<String> a = Mono.just("oops I'm late")
                .delaySubscription(Duration.ofMillis(450));
        Flux<String> b = Flux.just("let's get", "the party", "started")
                .delaySubscription(Duration.ofMillis(400));

        Flux.first(a, b)
                .toIterable()
                .forEach(System.out::println);
    }
}
