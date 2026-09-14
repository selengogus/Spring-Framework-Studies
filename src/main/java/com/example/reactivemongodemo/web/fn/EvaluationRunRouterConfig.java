package com.example.reactivemongodemo.web.fn;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class EvaluationRunRouterConfig {

    protected static final String EVALRUN_PATH = "/api/v3/evaluationRun";
    protected static final String EVALRUN_PATH_BY_ID = EVALRUN_PATH + "/{evalRunId}";

    private final EvaluationRunHandler handler;

    @Bean
    public RouterFunction<ServerResponse> evalRunRoutes() {
        return route()
                .GET(EVALRUN_PATH, accept(APPLICATION_JSON), handler::getEvalRuns)
                .GET(EVALRUN_PATH_BY_ID, accept(APPLICATION_JSON), handler::getEvalRun)
                .POST(EVALRUN_PATH, accept(APPLICATION_JSON), handler::createEvalRun)
                .PUT(EVALRUN_PATH_BY_ID, accept(APPLICATION_JSON), handler::updateEvalRun)
                .DELETE(EVALRUN_PATH_BY_ID, accept(APPLICATION_JSON), handler::deleteEvalRun)
                .build();
    }
}
