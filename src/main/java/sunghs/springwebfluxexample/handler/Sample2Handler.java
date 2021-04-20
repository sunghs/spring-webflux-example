package sunghs.springwebfluxexample.handler;

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
public class Sample2Handler {

    private final WebClient webClient;

    public Mono<ServerResponse> test2(ServerRequest serverRequest) {
        // 실제 데이터는 Mono 이지만 Flux 로 받음
        Flux<Dto> result1 = webClient.get().uri("/get").retrieve().bodyToFlux(Dto.class);
        // 실제 데이터는 Flux 이지만 Mono 로 받음 ==> Json START_ARRAY ([) 타입을 찾지 못해 파싱 에러가 발생
        Mono<Dto> result2 = webClient.post().uri("/postDoParallel").retrieve().bodyToMono(Dto.class);

        result1.subscribe(dto -> log.info("result1 : {}", dto.toString()));
        result2.subscribe(dto -> log.info("result2 : {}", dto.toString()));

        return ServerResponse.ok().build();
    }
}
