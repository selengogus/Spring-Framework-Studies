package com.example.reactivemongodemo.service.impl;

import com.example.reactivemongodemo.domain.EvaluationRun;
import com.example.reactivemongodemo.mapper.EvaluationRunMapper;
import com.example.reactivemongodemo.model.EvaluationRunDTO;
import com.example.reactivemongodemo.service.EvaluationRunService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest
class EvaluationRunServiceImplTest {

    @Container
    @ServiceConnection
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7");

    @Autowired
    private EvaluationRunService evaluationRunService;

    @Autowired
    private EvaluationRunMapper evaluationRunMapper;

    private EvaluationRunDTO dto;

    @BeforeEach
    private void setUp() {
        dto = getTestEvalRunDTO();
    }

    @Test
    void testSaveEvalRun() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        AtomicReference<EvaluationRunDTO> atomicDto = new AtomicReference<>();

        Mono<EvaluationRunDTO> savedMono = evaluationRunService.saveEvalRun(dto);

        savedMono.subscribe(
                savedDto -> {
                    System.out.println(savedDto.toString());
                    atomicBoolean.set(true);
                    atomicDto.set(savedDto);
                }
        );
        await().untilTrue(atomicBoolean);

        EvaluationRunDTO evaluationRunDTO = atomicDto.get();
        assertThat(evaluationRunDTO).isNotNull();
    }

    @Test
    void testGetEvalRuns() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        AtomicReference<List<EvaluationRunDTO>> dtoList = new AtomicReference<>();

        evaluationRunService.getEvalRuns()
                .collectList()
                .subscribe(
                evalRuns -> {
                    System.out.println("Inside subscribe");
                    dtoList.set(evalRuns);
                    atomicBoolean.set(true);
                }
        );

        await().untilTrue(atomicBoolean);
        System.out.println(dtoList.get());
    }

    @Test
    void testUpdateEvalRun() {
        EvaluationRunDTO existing = getTestEvalRunDTO();
        existing.setModel("test model");

        StepVerifier.create(evaluationRunService
                .saveEvalRun(existing).flatMap(dto ->
                    evaluationRunService.updateEvalRun(dto.getId(), getTestEvalRunDTO()))
                )
                .assertNext(updated -> {
                    assertThat(updated.getId()).isNotNull();
                    assertEquals("This is a test GPT-5", updated.getModel());
                })
                .verifyComplete();
    }

    @Test
    void testDeleteEvalRun() {

        StepVerifier.create(evaluationRunService.getEvalRuns()
                .next()
                .flatMap(evalRun ->
                    evaluationRunService.deleteEvalRun(evalRun.getId()).then(evaluationRunService.getEvalRun(evalRun.getId()))
                ))
                .verifyComplete();
    }

    @Test
    void testGetEvalRunByModel() {
        StepVerifier.create(evaluationRunService.getByEvaluationRunModel(getSavedEvalRun().getModel())
                ).thenConsumeWhile(evalRun -> {
                    assertThat(evalRun.getModel()).isEqualTo("This is a test GPT-5");
                    System.out.println(evalRun.toString());
                    return true;
                })
                .verifyComplete();
    }

    private EvaluationRunDTO getTestEvalRunDTO() {
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

    private EvaluationRun getSavedEvalRun() {
        return evaluationRunService.saveEvalRun(getTestEvalRunDTO())
                .map(evaluationRunMapper::DTOtoEvalRun)
                .block();
    }

}