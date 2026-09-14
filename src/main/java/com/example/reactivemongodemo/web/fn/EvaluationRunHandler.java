package com.example.reactivemongodemo.web.fn;

import com.example.reactivemongodemo.model.EvaluationRunDTO;
import com.example.reactivemongodemo.service.EvaluationRunService;
import com.mongodb.internal.connection.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import org.springframework.validation.Validator;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EvaluationRunHandler {

    private final EvaluationRunService evaluationRunService;
    private final Validator validator;

    private void validate(EvaluationRunDTO evaluationRunDTO) {
        Errors errors = new BeanPropertyBindingResult(evaluationRunDTO, "evaluationRunDTO");
        validator.validate(evaluationRunDTO, errors);

        if (errors.hasErrors()) {
            String msg = errors.getFieldErrors().stream()
                    .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                    .collect(Collectors.joining(", "));
            throw new ServerWebInputException(msg);
        }
    }

    public Mono<ServerResponse> getEvalRuns(ServerRequest request) {
        return evaluationRunService.getEvalRuns()
                .collectList()
                .flatMap(list -> list.isEmpty() ?
                        Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND))
                        : ServerResponse.ok().bodyValue(list));
    }

    public Mono<ServerResponse> getEvalRun(ServerRequest request) {
        return evaluationRunService.getEvalRun(
                request.pathVariable("evalRunId")
        )
                .flatMap(dto -> ServerResponse.ok().body(dto, EvaluationRunDTO.class))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    public Mono<ServerResponse> createEvalRun(ServerRequest request) {
        return request.bodyToMono(EvaluationRunDTO.class)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST)))
                .doOnNext(this::validate)
                .flatMap(evaluationRunService::saveEvalRun)
                .flatMap(savedDto -> ServerResponse
                        .created(UriComponentsBuilder
                                .fromPath(EvaluationRunRouterConfig.EVALRUN_PATH_BY_ID)
                                .buildAndExpand(savedDto.getId())
                                .toUri())
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(savedDto)
                        .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND))));
    }

    public Mono<ServerResponse> updateEvalRun(ServerRequest request) {
        return request.bodyToMono(EvaluationRunDTO.class)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST)))
                .doOnNext(this::validate)
                .flatMap(dto -> evaluationRunService.updateEvalRun(request.pathVariable("evalRunId"), dto))
                .flatMap(_ -> ServerResponse.noContent().build())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));

    }

    public Mono<ServerResponse> deleteEvalRun(ServerRequest request) {
        return evaluationRunService.deleteEvalRun(request.pathVariable("evalRunId"))
                .flatMap(_ -> ServerResponse.noContent().build())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }
}
