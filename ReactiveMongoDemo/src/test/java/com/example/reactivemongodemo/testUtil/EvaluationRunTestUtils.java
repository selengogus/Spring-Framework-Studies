package com.example.reactivemongodemo.testUtil;

import com.example.reactivemongodemo.model.EvaluationRunDTO;

import java.util.Map;

public final class EvaluationRunTestUtils {

    private EvaluationRunTestUtils() {}

    public static EvaluationRunDTO getTestEvalRunDTO() {
        return EvaluationRunDTO.builder()
                .model("This is a test GPT-5")
                .dataset("customer-support-v1")
                .promptVersion("v1")
                .parameters(Map.of(
                        "temperature", 0.2,
                        "maxTokens", 500.0
                ))
                .metrics(Map.of(
                        "accuracy", 0.91,
                        "hallucinationRate", 0.03
                ))
                .build();
    }
}
