package sunghs.springwebfluxexample.router;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import sunghs.springwebfluxexample.handler.SampleHandler;

@Configuration
@Slf4j
public class SampleRouter {

    @Bean
    public RouterFunction<ServerResponse> testRouter(SampleHandler sampleHandler) {
        return RouterFunctions.route()
            .nest(RequestPredicates.path("/"), builder -> builder.POST("/test", RequestPredicates.accept(MediaType.APPLICATION_JSON), sampleHandler::test))
            .build();
    }
}
