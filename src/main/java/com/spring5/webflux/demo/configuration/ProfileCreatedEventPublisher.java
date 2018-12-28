package com.spring5.webflux.demo.configuration;

import com.spring5.webflux.demo.events.ProfileCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.FluxSink;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

@Component
public class ProfileCreatedEventPublisher implements ApplicationListener<ProfileCreatedEvent>, Consumer<FluxSink<ProfileCreatedEvent>> {

    @Autowired
    private Executor executor;

    private final BlockingQueue<ProfileCreatedEvent> queue = new LinkedBlockingQueue<>();

    @Override
    public void onApplicationEvent(ProfileCreatedEvent profileCreatedEvent) {
        this.queue.offer(profileCreatedEvent);
    }

    @Override
    public void accept(FluxSink<ProfileCreatedEvent> profileCreatedEventFluxSink) {
        this.executor.execute(() -> {
            while (true)
                try {
                    ProfileCreatedEvent event = queue.take();
                    profileCreatedEventFluxSink.next(event);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        });
    }


}
