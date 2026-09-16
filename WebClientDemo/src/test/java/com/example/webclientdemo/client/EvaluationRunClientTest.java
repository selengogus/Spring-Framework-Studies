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
}
