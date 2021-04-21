package sunghs.springwebfluxexample;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
class MonoAndFluxTests {

    @Test
    void monoTest() {
        Mono.just("a").subscribe(s -> log.info("mono data : {}", s));
    }

    @Test
    void fluxConvertTest() {
        Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5);

        Assertions.assertEquals(5, Objects.requireNonNull(flux.collectList().block()).size());

        Objects.requireNonNull(flux.collectMap(integer -> integer).block(Duration.ofMillis(1000))).forEach(Assertions::assertEquals);
    }

    @Test
    @DisplayName("파일을 한번 읽어본다.")
    void fluxTest() {
        Flux<DataBuffer> data = DataBufferUtils.read(
            new DefaultResourceLoader().getResource("flux-data.txt"),
            new DefaultDataBufferFactory(),
            64);

        data.subscribe(dataBuffer -> log.info(dataBuffer.toString(StandardCharsets.UTF_8)));
    }

    @Test
    void stepVerifierTest() {
        StepVerifier.create(Flux.just("a", "b", "c", "d"))
            .expectSubscription()
            .expectNext("a")
            .expectNext("b")
            .expectNext("c")
            .expectNext("d")
            .verifyComplete();
    }

    @Test
    void rangeStepVerifierTest() {
        StepVerifier.create(Flux.range(1, 100))
            .expectSubscription()
            .expectNext(1)
            .expectNextCount(50) // 50개의 스트림을 건너뛴다
            .expectNext(52)
            .expectNextCount(47)
            .expectNext(100)
            .verifyComplete();
    }
}
