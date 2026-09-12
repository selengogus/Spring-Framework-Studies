package com.example.reactivemongodemo.repository;

import com.example.reactivemongodemo.domain.EvaluationRun;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface EvaluationRunRepository extends ReactiveMongoRepository<EvaluationRun, String> {

    Flux<EvaluationRun> getByModel(String model);
}
