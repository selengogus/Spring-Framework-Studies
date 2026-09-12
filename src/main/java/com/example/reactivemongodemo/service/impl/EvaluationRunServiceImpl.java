package com.example.reactivemongodemo.service.impl;

import com.example.reactivemongodemo.mapper.EvaluationRunMapper;
import com.example.reactivemongodemo.model.EvaluationRunDTO;
import com.example.reactivemongodemo.repository.EvaluationRunRepository;
import com.example.reactivemongodemo.service.EvaluationRunService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EvaluationRunServiceImpl implements EvaluationRunService {

    private final EvaluationRunRepository evaluationRunRepository;
    private final EvaluationRunMapper evaluationRunMapper;

    public EvaluationRunServiceImpl(EvaluationRunRepository evaluationRunRepository, EvaluationRunMapper evaluationRunMapper) {
        this.evaluationRunRepository = evaluationRunRepository;
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
        return evaluationRunRepository.findAll().map(evaluationRunMapper::evalRuntoDTO);
    }

    @Override
    public Mono<EvaluationRunDTO> getEvalRun(String id) {
        return evaluationRunRepository.findById(id).map(evaluationRunMapper::evalRuntoDTO);
    }

    @Override
    public Mono<Void> deleteEvalRun(String id) {
        return evaluationRunRepository.deleteById(id);
    }

    @Override
    public Flux<EvaluationRunDTO> getByEvaluationRunModel(String model) {
        return evaluationRunRepository.getByModel(model)
                .map(evaluationRunMapper::evalRuntoDTO);
    }
}
