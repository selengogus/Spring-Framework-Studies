package com.example.reactivemongodemo.web.fn;

import com.example.reactivemongodemo.model.EvaluationRunDTO;
import com.example.reactivemongodemo.service.EvaluationRunService;
import com.mongodb.internal.connection.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class EvaluationRunHandler {

    private final EvaluationRunService evaluationRunService;

    public Mono<ServerResponse> getEvalRuns(ServerRequest request) {
        return ServerResponse.ok().body(
                evaluationRunService.getEvalRuns(), EvaluationRunDTO.class
        );
    }

    public Mono<ServerResponse> getEvalRun(ServerRequest request) {
        return ServerResponse.ok().body(
                evaluationRunService.getEvalRun(request.pathVariable("evalRunId")), EvaluationRunDTO.class
        );
    }

    public Mono<ServerResponse> createEvalRun(ServerRequest request) {
        return request.bodyToMono(EvaluationRunDTO.class)
                .flatMap(evaluationRunService::saveEvalRun)
                .flatMap(savedDto -> ServerResponse
                        .created(UriComponentsBuilder
                                .fromPath(EvaluationRunRouterConfig.EVALRUN_PATH_BY_ID)
                                .buildAndExpand(savedDto.getId())
                                .toUri())
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(savedDto));
    }

    public Mono<ServerResponse> updateEvalRun(ServerRequest request) {
        return request.bodyToMono(EvaluationRunDTO.class)
                .flatMap(dto -> evaluationRunService.updateEvalRun(request.pathVariable("evalRunId"), dto))
                .then(ServerResponse.noContent().build());

    }

    public Mono<ServerResponse> deleteEvalRun(ServerRequest request) {
        return evaluationRunService.deleteEvalRun(request.pathVariable("evalRunId"))
                .then(ServerResponse.noContent().build());
    }
}
