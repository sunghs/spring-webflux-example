package sunghs.springwebfluxexample.service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import sunghs.springwebfluxexample.model.Dto;

@Slf4j
@Service
@RequiredArgsConstructor
public class SampleService {

    public Mono<String> setMono() {
        try {
            log.info("sleep start {}", LocalDateTime.now());
            Thread.sleep(3000);
        } catch (Exception e) {
            log.error("error", e);
        }
        log.info("sleep end {}", LocalDateTime.now());

        return Mono.just("test ok , " + LocalDateTime.now());
    }

    public Flux<Dto> setFlux() {
        return null;
    }
}
