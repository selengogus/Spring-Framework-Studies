package com.example.webclientdemo.client;

import com.example.webclientdemo.model.EvaluationRunDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tools.jackson.databind.JsonNode;

import java.util.Map;

@Service
public class EvaluationRunClientImpl implements EvaluationRunClient {

    private final WebClient webClient;

    private final String EVALRUN_PATH = "/api/v3/evaluationRun";
    private final String EVALRUN_PATH_BY_ID = EVALRUN_PATH + "/{evalRunId}";

    public EvaluationRunClientImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Override
    public Flux<String> getEvalRuns() {
        return webClient.get()
                .uri(EVALRUN_PATH)
                .retrieve()
                .bodyToFlux(String.class);
    }

    @Override
    public Flux<EvaluationRunDto> getEvalRunDtos() {
        return webClient.get()
                .uri(EVALRUN_PATH)
                .retrieve()
                .bodyToFlux(EvaluationRunDto.class);
    }

    @Override
    public Flux<JsonNode> getEvalRunJsonNode() {
        return webClient.get()
                .uri(EVALRUN_PATH)
                .retrieve()
                .bodyToFlux(JsonNode.class);
    }

    @Override
    public Flux<Map> getEvalRunMap() {
        return webClient.get()
                .uri(EVALRUN_PATH)
                .retrieve()
                .bodyToFlux(Map.class);
    }

    @Override
    public Mono<EvaluationRunDto> getById(String id) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(EVALRUN_PATH_BY_ID).build(id))
                .retrieve()
                .bodyToMono(EvaluationRunDto.class);
    }

    @Override
    public Flux<EvaluationRunDto> getByModel(String model) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(EVALRUN_PATH)
                        .queryParam("model", model)
                        .build())
                .retrieve()
                .bodyToFlux(EvaluationRunDto.class);
    }

    @Override
    public Flux<EvaluationRunDto> getByModelDataset(String model, String dataset) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(EVALRUN_PATH)
                        .queryParam("model", model)
                        .queryParam("dataset", dataset)
                        .build())
                .retrieve()
                .bodyToFlux(EvaluationRunDto.class);
    }

    @Override
    public Mono<EvaluationRunDto> createEvalRun(EvaluationRunDto evalRunDto) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder.path(EVALRUN_PATH).build())
                .bodyValue(evalRunDto)
                .retrieve()
                .bodyToMono(EvaluationRunDto.class);
    }

    @Override
    public Mono<Void> updateEvalRun(String id, EvaluationRunDto evalRunDto) {
        return webClient.put()
                .uri(uriBuilder -> uriBuilder.path(EVALRUN_PATH_BY_ID).build(id))
                .bodyValue(evalRunDto)
                .retrieve()
                .bodyToMono(Void.class);
    }

    @Override
    public Mono<Boolean> deleteEvalRun(String id) {
        return webClient.delete()
                .uri(uriBuilder -> uriBuilder.path(EVALRUN_PATH_BY_ID).build(id))
                .retrieve()
                .bodyToMono(Boolean.class);
    }
}
