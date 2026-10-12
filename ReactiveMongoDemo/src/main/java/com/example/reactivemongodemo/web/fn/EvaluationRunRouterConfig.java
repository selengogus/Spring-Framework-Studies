package com.example.reactivemongodemo.web.fn;

import com.example.reactivemongodemo.web.ApiPaths;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Profile("!controller")
@Configuration
@RequiredArgsConstructor
public class EvaluationRunRouterConfig {

    private final EvaluationRunHandler handler;

    @Bean
    public RouterFunction<ServerResponse> evalRunRoutes() {
        return route()
                .GET(ApiPaths.EVALRUN, accept(APPLICATION_JSON), handler::getEvalRuns)
                .GET(ApiPaths.EVALRUN_BY_ID, accept(APPLICATION_JSON), handler::getEvalRun)
                .POST(ApiPaths.EVALRUN, accept(APPLICATION_JSON), handler::createEvalRun)
                .PUT(ApiPaths.EVALRUN_BY_ID, accept(APPLICATION_JSON), handler::updateEvalRun)
                .DELETE(ApiPaths.EVALRUN_BY_ID, accept(APPLICATION_JSON), handler::deleteEvalRun)
                .build();
    }
}
