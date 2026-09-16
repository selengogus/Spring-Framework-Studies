package com.example.reactivemongodemo.service;

import com.example.reactivemongodemo.model.EvaluationRunDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EvaluationRunService {

    Mono<EvaluationRunDTO> saveEvalRun(EvaluationRunDTO dto);
    Mono<EvaluationRunDTO> updateEvalRun(String id, EvaluationRunDTO dto);
    Flux<EvaluationRunDTO> getEvalRuns();
    Mono<EvaluationRunDTO> getEvalRun(String id);
    Mono<Boolean> deleteEvalRun(String id);

    Flux<EvaluationRunDTO> getByEvaluationRunModel(String model);

}
