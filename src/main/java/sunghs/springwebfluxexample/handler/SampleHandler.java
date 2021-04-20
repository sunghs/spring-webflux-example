package sunghs.springwebfluxexample.handler;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
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

    /**
     * 2초가 걸림
     */
    public Mono<ServerResponse> get(ServerRequest serverRequest) {
        return ServerResponse.ok().bodyValue(generate(2000));
    }

    /**
     * 7초가 걸림
     */
    public Mono<ServerResponse> post(ServerRequest serverRequest) {
        return ServerResponse.ok().body(Flux.just(generate(1000), generate(5000), generate(1000)), Dto.class);
    }

    /**
     * 각 1, 5, 1초가 걸리지만 병렬로 만드므로 총 5초가 걸림
     */
    public Mono<ServerResponse> postDoParallel(ServerRequest serverRequest) {
        return ServerResponse.ok().body(Flux.just(generate(1000), generate(5000), generate(1000)).parallel(), Dto.class);
    }

    public Mono<ServerResponse> test(ServerRequest serverRequest) {
        LocalDateTime start = LocalDateTime.now();
        log.info("method start");

        Mono<Dto> result1 = webClient.get().uri("/get").retrieve().bodyToMono(Dto.class);
        // 7초가 걸리는 flux
        // Flux<Dto> result2 = webClient.post().uri("/post").retrieve().bodyToFlux(Dto.class);
        // 5초가 걸리는 flux
        Flux<Dto> result2 = webClient.post().uri("/postDoParallel").retrieve().bodyToFlux(Dto.class);

        result1.subscribe(dto -> log.info("result1 : {}", dto.toString()));
        result2.subscribe(dto -> log.info("result2 : {}", dto.toString()));

        // 구독할게 없으므로 start와 end가 거의 동시에 찍힌다.
        // return ServerResponse.ok().body(BodyInserters.fromValue("start : " + start.toString() + ", end : " + LocalDateTime.now().toString()));

        // result1 과 result2를 묶는다, 둘을 비동기로 실행해서 max time 뒤 return 한다.
        return ServerResponse.created(serverRequest.uri()).body(Flux.zip(result1, result2), Dto.class);
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
