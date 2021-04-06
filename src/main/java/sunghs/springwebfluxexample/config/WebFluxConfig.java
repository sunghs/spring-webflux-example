package sunghs.springwebfluxexample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;

@EnableWebFlux
public class WebFluxConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
