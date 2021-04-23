package sunghs.springwebfluxexample.handler;

import java.time.Duration;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class StreamingHandler {

    public Mono<ServerResponse> streaming(ServerRequest serverRequest) {
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
            .body(BodyInserters.fromServerSentEvents(
                Flux.interval(Duration.ofSeconds(1L))
                    .map(aLong -> ServerSentEvent.builder()
                        .id(String.valueOf(aLong))
                        .event("test " + aLong)
                        .data(LocalDateTime.now())
                        .build()
                    )
            ));
    }

    public Mono<ServerResponse> streamingFile(ServerRequest serverRequest) {
        Flux<DataBuffer> data = DataBufferUtils.read(new DefaultResourceLoader().getResource("flux-data.txt"), new DefaultDataBufferFactory(), 1);
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(BodyInserters.fromDataBuffers(data));
    }
}
