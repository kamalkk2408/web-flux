package com.kishore.kamal.webflux.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

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
}
