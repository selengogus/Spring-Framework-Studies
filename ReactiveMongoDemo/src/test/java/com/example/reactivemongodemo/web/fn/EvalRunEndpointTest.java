package com.example.reactivemongodemo.web.fn;

import com.example.reactivemongodemo.model.EvaluationRunDTO;
import com.example.reactivemongodemo.repository.EvaluationRunRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;

import static com.example.reactivemongodemo.testUtil.EvaluationRunTestUtils.getTestEvalRunDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testcontainers.shaded.org.hamcrest.collection.IsCollectionWithSize.hasSize;

@Testcontainers
@SpringBootTest
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class EvalRunEndpointTest {

    public static final String EVALRUN_PATH = "/api/v3/evaluationRun";
    public static final String EVALRUN_PATH_BY_ID = EVALRUN_PATH + "/{evalRunId}";

    @ServiceConnection
    @Container
    private static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7");

    @Autowired
    private WebTestClient webTestClient;

    @Nested
    class DataExists {
        @Test
        public void testGetEvalRuns() {
            webTestClient.get()
                    .uri(EVALRUN_PATH)
                    .exchange()
                    .expectStatus().isOk()
                    .expectHeader().valueEquals("Content-type", "application/json")
                    .expectBodyList(EvaluationRunDTO.class)
                    .value(runs -> assertThat(runs).hasSizeGreaterThan(1));
        }

        @Test
        public void testCreateEvalRun() {
            webTestClient.post()
                    .uri(EVALRUN_PATH)
                    .body(Mono.just(getTestEvalRunDTO()), EvaluationRunDTO.class)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectHeader().valueEquals("Content-type", "application/json")
                    .expectBody(EvaluationRunDTO.class)
                    .value(run -> assertThat(run).isNotNull());
        }
    }

    @Nested
    class DataNotFound {

        @Autowired
        private EvaluationRunRepository evaluationRunRepository;

        @BeforeEach
        void clearData() {
            evaluationRunRepository.deleteAll().block();
        }

        @Test
        void testGetEvalRunsReturnsNotFound() {
            webTestClient.get()
                    .uri(EVALRUN_PATH)
                    .exchange()
                    .expectStatus().isNotFound();
        }

        @Test
        void testCreateEvalRunReturnsBadRequest() {
            webTestClient.post()
                    .uri(EVALRUN_PATH)
                    .body(Mono.empty(), EvaluationRunDTO.class)
                    .exchange()
                    .expectStatus().isBadRequest();
        }
    }
}
