package com.kishore.kamal.webflux.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {

    @Test
    public void fluxTest(){
        Flux<String> flux = Flux.just("Spring", "Spring boot", "Reactive Spring")
//                .concatWith(Flux.error(new RuntimeException("Exception occured")))
                .log();

        flux.subscribe(System.out::println,
                (e) -> System.err.println("Exception is "+e),
                () -> System.out.println("Completed"));
    }

    @Test
    public void fluxTestElement_withoutError(){
        Flux<String> flux = Flux.just("Spring", "Spring boot", "Reactive Spring")
//                .concatWith(Flux.error(new RuntimeException("Exception occured")))
                .log();

        StepVerifier.create(flux).expectNext("Spring").expectNext("Spring boot").expectNext("Reactive Spring").verifyComplete();
    }

    @Test
    public void fluxTestElement_withError(){
        Flux<String> flux = Flux.just("Spring", "Spring boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception occured")))
                .log();

        StepVerifier.create(flux).expectNext("Spring").expectNext("Spring boot").expectNext("Reactive Spring").expectError(RuntimeException.class);
    }

    @Test
    public void fluxTestElement_withError1(){
        Flux<String> flux = Flux.just("Spring", "Spring boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Exception occured")))
                .log();

        StepVerifier.create(flux).expectNext("Spring", "Spring boot", "Reactive Spring").expectError(RuntimeException.class);
    }

    @Test
    public void monoTest(){
        Mono<String> mono = Mono.just("Spring");
        StepVerifier.create(mono.log()).expectNext("Spring").verifyComplete();
    }

    @Test
    public void monoTest1(){
        StepVerifier.create(Mono.error(new RuntimeException("Exception occured")).log()).expectError(RuntimeException.class).verify();
    }
}
