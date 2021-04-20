package sunghs.springwebfluxexample.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import sunghs.springwebfluxexample.model.Dto;

@Slf4j
@Component
@RequiredArgsConstructor
public class Sample2Handler {

    private final WebClient webClient;

    public Mono<ServerResponse> test2(ServerRequest serverRequest) {
        Mono<Dto> request = serverRequest.bodyToMono(Dto.class);

        return ServerResponse.ok().body(BodyInserters.fromValue("sample2 ok"));
    }
}
