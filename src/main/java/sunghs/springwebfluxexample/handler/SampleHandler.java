package sunghs.springwebfluxexample.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import sunghs.springwebfluxexample.model.Dto;
import sunghs.springwebfluxexample.service.SampleService;

@Slf4j
@Component
@RequiredArgsConstructor
public class SampleHandler {

    private final SampleService sampleService;

    public Mono<ServerResponse> get(ServerRequest serverRequest) {
        Dto dto = Dto.builder()
            .seq(1)
            .name("sunghs")
            .build();

        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromProducer(Mono.just(dto), Dto.class));
    }

    public Mono<ServerResponse> post(ServerRequest serverRequest) {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromProducer(sampleService.setMono(), String.class));
    }
}
