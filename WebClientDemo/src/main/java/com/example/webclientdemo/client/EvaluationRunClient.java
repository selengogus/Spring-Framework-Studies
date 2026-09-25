package com.example.webclientdemo.client;

import com.example.webclientdemo.model.EvaluationRunDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;

import java.util.Map;

public interface EvaluationRunClient {
    Flux<String> getEvalRuns();
    Flux<Map> getEvalRunMap();
    Flux<JsonNode> getEvalRunJsonNode();
    Flux<EvaluationRunDto> getEvalRunDtos();
    Mono<EvaluationRunDto> getById(String id);
    Flux<EvaluationRunDto> getByModel(String model);
    Flux<EvaluationRunDto> getByModelDataset(String model, String dataset);
    Mono<EvaluationRunDto> createEvalRun(EvaluationRunDto evalRunDto);
    Mono<Void> updateEvalRun(String id, EvaluationRunDto evalRunDto);
    Mono<Boolean> deleteEvalRun(String id);
}
