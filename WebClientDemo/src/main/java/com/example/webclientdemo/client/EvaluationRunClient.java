package com.example.webclientdemo.client;

import reactor.core.publisher.Flux;
import tools.jackson.databind.JsonNode;

import java.util.Map;

public interface EvaluationRunClient {
    Flux<String> getEvalRuns();
    Flux<Map> getEvalRunMap();
    Flux<JsonNode> getEvalRunJsonNode();
}
