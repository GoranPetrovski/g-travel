package com.spring5.webflux.demo.websocket;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.time.Duration;

import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Mono;


public class WebSocketClientTest {
    @Log4j2
    @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
    public class WebSocketConfigurationTest {

        private final WebSocketClient client = new ReactorNettyWebSocketClient();

        @Test
        public void testSocketNotification() throws Exception {
            client.execute(
                    URI.create("ws://localhost:8080/event-emitter"),
                    session -> session.send(
                            Mono.just(session.textMessage("event-spring-reactive-client-websocket")))
                            .thenMany(session.receive()
                                    .map(WebSocketMessage::getPayloadAsText)
                                    .log())
                            .then())
                    .block(Duration.ofSeconds(10L));
        }
    }
}
