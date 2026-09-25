package com.example.webclientdemo.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    public void testCreateEvalRun() {
        StepVerifier.create(evaluationRunClient.getEvalRunDtos()
                        .next()
                .flatMap(dto -> evaluationRunClient.createEvalRun(dto))
                        .flatMap(created -> evaluationRunClient.getById(created.getId())))
                .assertNext(fetchedDto -> {
                    assertThat(fetchedDto).isNotNull();
                    assertThat(fetchedDto.getId()).isNotNull();
                })
                .verifyComplete();
    }

    @Test
    public void testUpdateEvalRun() {
        StepVerifier.create(evaluationRunClient.getEvalRunDtos()
                .next()
                .flatMap(dto -> {
                    dto.setModel("this is a test model");
                    return Mono.just(dto);
                })
                .flatMap(newDto -> evaluationRunClient.updateEvalRun(newDto.getId(), newDto)))
                .verifyComplete();
    }

    @Test
    public void testDeleteEvalRun() {
        StepVerifier.create(
                        evaluationRunClient.getEvalRunDtos()
                                .next()
                                .flatMap(dto ->
                                        evaluationRunClient.deleteEvalRun(dto.getId())
                                                .thenReturn(dto.getId())
                                )
                                .flatMap(id -> evaluationRunClient.getById(id))
                )
                .expectError(WebClientResponseException.NotFound.class)
                .verify();

    }
}
