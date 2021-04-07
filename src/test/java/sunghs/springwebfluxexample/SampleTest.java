package sunghs.springwebfluxexample;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import sunghs.springwebfluxexample.handler.SampleHandler;
import sunghs.springwebfluxexample.router.SampleRouter;
import sunghs.springwebfluxexample.service.SampleService;

@AutoConfigureWebFlux
@AutoConfigureWebTestClient
@ContextConfiguration(classes = {
    SampleHandler.class,
    SampleRouter.class,
    SampleService.class
})
@TestConstructor(autowireMode = AutowireMode.ALL)
@RequiredArgsConstructor
@WebFluxTest
@Slf4j
public class SampleTest {

    private final WebTestClient webTestClient;

    private final WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080")
        .build();

    @BeforeEach
    public void beforeEach() {

    }

    @Test
    public void webClientTest() {
        for (int i = 0; i < 10; i++) {
            log.info("set : {}", LocalDateTime.now());

            webClient.post()
                .uri("/post")
                .retrieve()
                .bodyToMono(String.class)
                .log()
                .subscribe(s -> log.info("{}, {}", LocalDateTime.now(), s));
        }
    }

    @Test
    public void webTestClientTest() {
        webTestClient.post()
            .uri("/post")
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class);
    }

    @AfterEach
    public void afterEach() {

    }
}
