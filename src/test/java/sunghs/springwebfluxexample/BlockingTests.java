package sunghs.springwebfluxexample;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;
import sunghs.springwebfluxexample.model.Dto;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestConstructor(autowireMode = AutowireMode.ALL)
@RequiredArgsConstructor
@Slf4j
class BlockingTests {

    private final WebClient webClient;

    @Test
    @DisplayName("webClient의 비동기성으로 인해 수행시간이 9초가 아닌 7초인지")
    void webClientFluxTest() {
        LocalDateTime start = LocalDateTime.now();
        // 2초
        Mono<Dto> result1 = webClient.get().uri("/get").retrieve().bodyToMono(Dto.class);
        // 7초
        Flux<Dto> result2 = webClient.post().uri("/post").retrieve().bodyToFlux(Dto.class);

        int expectedSecond = 7;
        int underSecond = 9;

        Flux<Tuple2<Dto, Dto>> result = Flux.zip(result1, result2);
        result.subscribe(objects -> {
            LocalDateTime expected = start.plusSeconds(expectedSecond);
            LocalDateTime under = start.plusSeconds(underSecond);
            Assertions.assertTrue(expected.isBefore(under));
            log.info("start {}, expected {}, under {}", start, expected, under);
        });

        StepVerifier.create(result)
            .expectSubscription()
            .expectNextCount(1) // Tuple의 카운트이므로 4개가 아닌 1개가 전부이다.
            .expectComplete()
            .verify();
    }

    @Test
    @DisplayName("7초짜리 Flux 생성 시 병렬처리를 해서 maxTime이 5초가 걸리는지")
    void webClientParallelFluxTest() {
        LocalDateTime start = LocalDateTime.now();
        // 2초
        Mono<Dto> result1 = webClient.get().uri("/get").retrieve().bodyToMono(Dto.class);
        // 5초
        Flux<Dto> result2 = webClient.post().uri("/postDoParallel").retrieve().bodyToFlux(Dto.class);

        int expectedSecond = 5;
        int underSecond = 6;

        Flux<Tuple2<Dto, Dto>> result = Flux.zip(result1, result2);
        result.subscribe(objects -> {
            LocalDateTime expected = start.plusSeconds(expectedSecond);
            LocalDateTime under = start.plusSeconds(underSecond);
            Assertions.assertTrue(expected.isBefore(under));
            log.info("start {}, expected {}, under {}", start, expected, under);
        });

        StepVerifier.create(result)
            .expectSubscription()
            .expectNextCount(1) // Tuple의 카운트이므로 4개가 아닌 1개가 전부이다.
            .expectComplete()
            .verify();
    }
}
