package sunghs.springwebfluxexample;

import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
class MonoAndFluxTests {

    @Test
    void monoTest() {
        Mono.just("a").subscribe(s -> log.info("mono data : {}", s));
    }

    @Test
    void fluxTest() {
        Flux<DataBuffer> data = DataBufferUtils.read(
            new DefaultResourceLoader().getResource("flux-data.txt"),
            new DefaultDataBufferFactory(),
            64);

        data.subscribe(dataBuffer -> log.info(dataBuffer.toString(StandardCharsets.UTF_8)));
    }
}
