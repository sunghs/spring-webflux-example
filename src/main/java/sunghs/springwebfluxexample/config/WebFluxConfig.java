package sunghs.springwebfluxexample.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import sunghs.springwebfluxexample.model.Dto;

@Configuration
@Slf4j
public class WebFluxConfig {

    private static final String EXTERNAL_URL = "https://input-your-url";
    @Bean
    public WebClient webLoggingClient() {
        return WebClient.builder()
            .baseUrl("http://localhost:8080")
            .clientConnector(new ReactorClientHttpConnector(HttpClient.create()))
            .filter(ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
                log.info("request method : {}, uri : {}", clientRequest.method(), clientRequest.url());
                return Mono.just(clientRequest);
            }))
            .filter(ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
                log.info("response : {}", clientResponse.bodyToFlux(Dto.class).toString());
                return Mono.just(clientResponse);
            }))
            .build();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
            .baseUrl("http://localhost:8080")
            .clientConnector(new ReactorClientHttpConnector(HttpClient.create()))
            .build();
    }

    @Bean
    public WebClient webMessageClient() {
        return WebClient.builder()
            .baseUrl(EXTERNAL_URL)
            .filter(ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
                log.info("request method : {}, uri : {}", clientRequest.method(), clientRequest.url().getPath());
                return Mono.just(clientRequest);
            }))
            .clientConnector(new ReactorClientHttpConnector(HttpClient.create()))
            .build();
    }
}
