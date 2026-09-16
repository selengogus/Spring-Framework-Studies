package com.example.webclientdemo.client;

import reactor.core.publisher.Flux;

public interface EvaluationRunClient {
    Flux<String> getEvalRuns();
}
