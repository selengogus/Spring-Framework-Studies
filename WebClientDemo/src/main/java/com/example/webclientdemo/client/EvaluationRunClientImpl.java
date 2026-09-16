package com.example.webclientdemo.client;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class EvaluationRunClientImpl implements EvaluationRunClient {

    private final WebClient webClient;

    public EvaluationRunClientImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    @Override
    public Flux<String> getEvalRuns() {
        return webClient.get()
                .uri("/api/v3/evaluationRun")
                .retrieve()
                .bodyToFlux(String.class);
    }
}
