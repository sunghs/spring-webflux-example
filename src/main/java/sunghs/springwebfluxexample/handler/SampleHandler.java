package sunghs.springwebfluxexample.handler;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sunghs.springwebfluxexample.model.Dto;

@Slf4j
@Component
@RequiredArgsConstructor
public class SampleHandler {

    private final WebClient webClient;

    public Mono<ServerResponse> get(ServerRequest serverRequest) {
        return ServerResponse.ok().bodyValue(generate(2000));
    }

    public Mono<ServerResponse> post(ServerRequest serverRequest) {
        return ServerResponse.ok().body(Flux.just(generate(1000), generate(5000), generate(1000)), Dto.class);
    }

    public Mono<ServerResponse> test(ServerRequest serverRequest) {
        LocalDateTime start = LocalDateTime.now();

        Mono<Dto> result1 = webClient.get().uri("/get").retrieve().bodyToMono(Dto.class);
        Flux<Dto> result2 = webClient.post().uri("/post").retrieve().bodyToFlux(Dto.class);

        result1.subscribe(dto -> log.info("result1 : {}", dto.toString()));
        result2.subscribe(dto -> log.info("result2 : {}", dto.toString()));

        //return ServerResponse.created(serverRequest.uri()).body(result2, Dto.class);
        return ServerResponse.ok().body(BodyInserters.fromValue("start : " + start.toString() + ", end : " + LocalDateTime.now().toString()));
    }

    private void sleep(long i) {
        try {
            Thread.sleep(i);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }

    private Dto generate(long i) {
        sleep(i);
        return Dto.builder()
            .seq(new SecureRandom().nextInt(9999))
            .name(UUID.randomUUID().toString().replaceAll("-", ""))
            .build();
    }
}
