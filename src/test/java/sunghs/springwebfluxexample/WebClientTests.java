package sunghs.springwebfluxexample;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
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
public class WebClientTests {

}
