package sunghs.springwebfluxexample;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.test.web.reactive.server.WebTestClient;
import sunghs.springwebfluxexample.handler.SampleHandler;
import sunghs.springwebfluxexample.router.SampleRouter;
import sunghs.springwebfluxexample.service.SampleService;

@TestConstructor(autowireMode = AutowireMode.ALL)
@RequiredArgsConstructor
@SpringBootTest(classes = {
    SampleHandler.class,
    SampleRouter.class,
    SampleService.class
})
@Slf4j
public class SampleTest {

    private WebTestClient webTestClient;

    private final SampleRouter sampleRouter;

    private final SampleHandler sampleHandler;

    @BeforeEach
    public void beforeEach() {
        webTestClient = WebTestClient
            .bindToRouterFunction(sampleRouter.testRouter(sampleHandler))
            .build();
    }

    @Test
    public void test() {
        webTestClient.post()
            .uri("/test")
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
            .value(log::info);
    }
}
