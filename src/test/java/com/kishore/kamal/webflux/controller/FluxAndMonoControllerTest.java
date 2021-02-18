package com.kishore.kamal.webflux.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebFluxTest
public class FluxAndMonoControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void returnFlux_test1(){
        Flux<Integer> streamFlux = webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
        .returnResult(Integer.class).getResponseBody();

        StepVerifier.create(streamFlux)
                .expectSubscription()
                .expectNext(1,2 ,3,4)
                .verifyComplete();
    }

    @Test
    public void returnFlux_test2(){
        webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectBodyList(Integer.class).hasSize(4);

    }

    @Test
    public void returnFlux_test3(){
        List<Integer> expectedIntegerList = Arrays.asList(1,2,3,4);
        EntityExchangeResult<List<Integer>> entityExchangeResult=  webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectBodyList(Integer.class)
                .returnResult();
        assertEquals(entityExchangeResult.getResponseBody(), expectedIntegerList);
    }

    @Test
    public void returnFlux_test4(){
        List<Integer> expectedIntegerList = Arrays.asList(1,2,3,4);
        webTestClient.get().uri("/flux")
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectBodyList(Integer.class)
                .consumeWith((response) -> {
                    assertEquals(response.getResponseBody(), expectedIntegerList);
                });
    }

    @Test
    public void returnMono_test1(){
        Integer expected = new Integer(1);
         webTestClient.get().uri("/mono")
                .accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectBody(Integer.class).consumeWith((response) ->assertEquals(expected, response.getResponseBody()) );
    }

}
