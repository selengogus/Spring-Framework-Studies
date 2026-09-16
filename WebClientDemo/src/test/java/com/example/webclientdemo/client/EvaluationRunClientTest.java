package com.example.webclientdemo.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@SpringBootTest
public class EvaluationRunClientTest {

    @Autowired
    private EvaluationRunClient evaluationRunClient;

    @Test
    public void testGetEvalRuns() {
        StepVerifier.create(evaluationRunClient.getEvalRuns())
                .thenConsumeWhile(run -> {
                    System.out.println(run);
                    return true;
                })
                .verifyComplete();
    }

    @Test
    public void testGetEvalRunMap() {
        StepVerifier.create(evaluationRunClient.getEvalRunMap())
                .thenConsumeWhile(run -> {
                    System.out.println(run);
                    return true;
                })
                .verifyComplete();
    }

    @Test
    public void testGetEvalJsonNode() {
        StepVerifier.create(evaluationRunClient.getEvalRunJsonNode())
                .thenConsumeWhile(run -> {
                    System.out.println(run.toPrettyString());
                    return true;
                })
                .verifyComplete();
    }
}
