package sunghs.springwebfluxexample.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import sunghs.springwebfluxexample.service.SampleService;

@Component
@RequiredArgsConstructor
@Slf4j
public class SampleHandler {

    private final SampleService sampleService;

    public Mono<ServerResponse> test(ServerRequest serverRequest) {
        return ServerResponse
            .ok()
            .body(BodyInserters.fromProducer(sampleService.test(), String.class));
    }
}
