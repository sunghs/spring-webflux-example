package sunghs.springwebfluxexample;

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

    @BeforeEach
    public void beforeEach() {

    }

    @Test
    public void test() {
        for(int i = 0; i < 10; i ++) {
            webTestClient.post()
                .uri("/test")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class);
        }
    }

    @AfterEach
    public void afterEach() {

    }
}
