package sunghs.springwebfluxexample.service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
@Slf4j
public class SampleService {

    public Mono<String> test() {
        try {
            log.info("sleep start {}", LocalDateTime.now());
            Thread.sleep(3000);
        } catch (Exception e) {
            log.error("error", e);
        }
        log.info("sleep end {}", LocalDateTime.now());

        return Mono.just("test ok , " + LocalDateTime.now());
    }
}
