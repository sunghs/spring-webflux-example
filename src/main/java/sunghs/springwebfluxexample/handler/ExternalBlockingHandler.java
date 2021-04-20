package sunghs.springwebfluxexample.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import sunghs.springwebfluxexample.model.TestRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExternalBlockingHandler {

    private final WebClient webMessageClient;

    public Mono<ServerResponse> test(ServerRequest serverRequest) {
        for (int i = 0; i < 20; i ++) {
            webMessageClient.post().uri("insert your uri")
                .body(BodyInserters.fromValue(new TestRequest(i)))
                .retrieve()
                .bodyToFlux(String.class).subscribe(this::getReturnStatus);
        }
        return ServerResponse.ok().build();
    }
    private void getReturnStatus(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            log.info("status {}, code {}", jsonObject.get("returnStatus"), jsonObject.get("returnCode"));
        } catch (Exception e) {
            log.error("", e);
        }
    }
}


