package com.example.webclientdemo.client;

import com.example.webclientdemo.model.EvaluationRunDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import tools.jackson.databind.JsonNode;

import java.util.Map;

@Service
public class EvaluationRunClientImpl implements EvaluationRunClient {

    private final WebClient webClient;

    private final String EVALRUN_PATH = "/api/v3/evaluationRun";

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
}
