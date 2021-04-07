package sunghs.springwebfluxexample.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import sunghs.springwebfluxexample.handler.SampleHandler;

@Configuration
public class SampleRouter implements WebFluxConfigurer {

    @Bean
    public RouterFunction<ServerResponse> testRouter(SampleHandler sampleHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/get").and(RequestPredicates.contentType(MediaType.ALL)), sampleHandler::get)
            .andRoute(RequestPredicates.POST("/post").and(RequestPredicates.contentType(MediaType.ALL)), sampleHandler::post);
    }
}
