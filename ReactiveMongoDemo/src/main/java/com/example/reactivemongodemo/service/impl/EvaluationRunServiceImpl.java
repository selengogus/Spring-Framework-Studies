package com.example.reactivemongodemo.service.impl;

import com.example.reactivemongodemo.domain.EvaluationRun;
import com.example.reactivemongodemo.mapper.EvaluationRunMapper;
import com.example.reactivemongodemo.model.EvaluationRunDTO;
import com.example.reactivemongodemo.repository.EvaluationRunRepository;
import com.example.reactivemongodemo.service.EvaluationRunService;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EvaluationRunServiceImpl implements EvaluationRunService {

    private final EvaluationRunRepository evaluationRunRepository;
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final EvaluationRunMapper evaluationRunMapper;

    public EvaluationRunServiceImpl(EvaluationRunRepository evaluationRunRepository, ReactiveMongoTemplate reactiveMongoTemplate, EvaluationRunMapper evaluationRunMapper) {
        this.evaluationRunRepository = evaluationRunRepository;
        this.reactiveMongoTemplate = reactiveMongoTemplate;
        this.evaluationRunMapper = evaluationRunMapper;
    }

    @Override
    public Mono<EvaluationRunDTO> saveEvalRun(EvaluationRunDTO dto) {
        return evaluationRunRepository
                .save(evaluationRunMapper.DTOtoEvalRun(dto))
                .map(evaluationRunMapper::evalRuntoDTO);
    }

    @Override
    public Mono<EvaluationRunDTO> updateEvalRun(String id, EvaluationRunDTO dto) {
        return evaluationRunRepository.findById(id).map(
                evalRun -> {
                       evalRun.setModel(dto.getModel());
                       evalRun.setDataset(dto.getDataset());
                       evalRun.setMetrics(dto.getMetrics());
                       evalRun.setParameters(dto.getParameters());
                       return evalRun;
                }
        )
                .flatMap(evaluationRunRepository::save)
                .map(evaluationRunMapper::evalRuntoDTO);
    }

    @Override
    public Flux<EvaluationRunDTO> getEvalRuns() {
        return getEvalRuns(null, null, null);
    }

    @Override
    public Flux<EvaluationRunDTO> getEvalRuns(String model, String dataset, Double minAccuracy) {

        Query query = new Query();

        if(model != null ) query.addCriteria(Criteria.where("model").is(model));
        if(dataset != null ) query.addCriteria(Criteria.where("dataset").is(dataset));
        if(minAccuracy != null ) query.addCriteria(Criteria.where("metrics.accuracy").gte(minAccuracy));

        return reactiveMongoTemplate.find(query, EvaluationRun.class)
                .map(evaluationRunMapper::evalRuntoDTO);
    }

    @Override
    public Mono<EvaluationRunDTO> getEvalRun(String id) {
        return evaluationRunRepository.findById(id).map(evaluationRunMapper::evalRuntoDTO);
    }

    @Override
    public Mono<Boolean> deleteEvalRun(String id) {

        return evaluationRunRepository.existsById(id)
                .flatMap(exists ->
                        exists ?
                        evaluationRunRepository.deleteById(id).thenReturn(true)
                        : Mono.just(false));
    }

    @Override
    public Flux<EvaluationRunDTO> getByEvaluationRunModel(String model) {
        return evaluationRunRepository.getByModel(model)
                .map(evaluationRunMapper::evalRuntoDTO);
    }
}
