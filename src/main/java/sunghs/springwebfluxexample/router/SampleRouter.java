package sunghs.springwebfluxexample.router;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import sunghs.springwebfluxexample.handler.SampleHandler;

@Configuration
public class SampleRouter {

    public RouterFunction<ServerResponse> testRouter(SampleHandler sampleHandler) {
        return RouterFunctions.route(RequestPredicates.POST("/test"), sampleHandler::test);
    }
}
