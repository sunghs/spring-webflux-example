package sunghs.springwebfluxexample.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import sunghs.springwebfluxexample.handler.ExternalBlockingHandler;
import sunghs.springwebfluxexample.handler.Sample2Handler;
import sunghs.springwebfluxexample.handler.SampleHandler;
import sunghs.springwebfluxexample.handler.StreamingHandler;

@Configuration
public class SampleRouter implements WebFluxConfigurer {

    @Bean
    public RouterFunction<ServerResponse> routerFunction1(SampleHandler sampleHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/get").and(RequestPredicates.contentType(MediaType.ALL)), sampleHandler::get)
            .andRoute(RequestPredicates.POST("/post").and(RequestPredicates.contentType(MediaType.ALL)), sampleHandler::post)
            .andRoute(RequestPredicates.POST("/postDoParallel").and(RequestPredicates.contentType(MediaType.ALL)), sampleHandler::postDoParallel)
            .andRoute(RequestPredicates.GET("/test").and(RequestPredicates.contentType(MediaType.ALL)), sampleHandler::test);
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction2(Sample2Handler sample2Handler) {
        return RouterFunctions.route(RequestPredicates.GET("/test2").and(RequestPredicates.contentType(MediaType.ALL)), sample2Handler::test2);
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction3(ExternalBlockingHandler externalBlockingHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/ext").and(RequestPredicates.contentType(MediaType.ALL)), externalBlockingHandler::test);
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction4(StreamingHandler streamingHandler) {
        return RouterFunctions.route(RequestPredicates.GET("/streaming").and(RequestPredicates.contentType(MediaType.ALL)), streamingHandler::streaming)
            .andRoute(RequestPredicates.GET("/streaming2").and(RequestPredicates.contentType(MediaType.ALL)), streamingHandler::streamingFile);
    }
}
