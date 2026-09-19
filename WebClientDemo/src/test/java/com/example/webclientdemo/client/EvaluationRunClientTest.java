package com.example.webclientdemo.client;

import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.netty.udp.UdpOutbound;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

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
    public void testGetEvalRunJsonNode() {
        StepVerifier.create(evaluationRunClient.getEvalRunJsonNode())
                .thenConsumeWhile(run -> {
                    System.out.println(run.toPrettyString());
                    return true;
                })
                .verifyComplete();
    }

    @Test
    public void testGetEvalRunDtos() {
        StepVerifier.create(evaluationRunClient.getEvalRunDtos())
                .thenConsumeWhile(run -> {
                    System.out.println(run);
                    return true;
                })
                .verifyComplete();
    }

    @Test
    public void testGetById() {
        StepVerifier.create(evaluationRunClient.getEvalRunDtos()
                .flatMap(dto -> {
                    return evaluationRunClient.getById(dto.getId());
                }))
                .thenConsumeWhile(dto -> {
                    System.out.println(dto);
                    return true;
                })
                .verifyComplete();
    }

    @Test
    public void testGetByModel() {
        String model = "Claude-4";
        StepVerifier.create(evaluationRunClient.getByModel(model))
                .thenConsumeWhile(run -> {
                    System.out.println(run);
                    assertEquals(model, run.getModel());
                    return true;
                })
                .verifyComplete();
    }

    @Test
    public void testGetByModelDataset() {
        String model = "Claude-4";
        String dataset = "customer-support-v2";
        StepVerifier.create(evaluationRunClient.getByModelDataset(model, dataset))
                .thenConsumeWhile(run -> {
                    System.out.println(run);
                    assertEquals(model, run.getModel());
                    assertEquals(dataset, run.getDataset());
                    return true;
                })
                .verifyComplete();
    }
}
