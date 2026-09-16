package com.example.reactivemongodemo.csv.csvService.Impl;

import com.example.reactivemongodemo.csv.converter.EvaluationRunCsvConverter;
import com.example.reactivemongodemo.csv.csvModel.EvaluationRunCsv;
import com.example.reactivemongodemo.csv.csvReader.EvaluationRunCsvReader;
import com.example.reactivemongodemo.csv.csvService.EvaluationRunCsvService;
import com.example.reactivemongodemo.domain.EvaluationRun;
import com.example.reactivemongodemo.mapper.EvaluationRunMapper;
import com.example.reactivemongodemo.model.EvaluationRunDTO;
import com.example.reactivemongodemo.repository.EvaluationRunRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationRunCsvServiceImpl implements EvaluationRunCsvService<EvaluationRunDTO> {

    private final EvaluationRunRepository evaluationRunRepository;
    private final EvaluationRunMapper evaluationRunMapper;
    private final EvaluationRunCsvReader csvReader;
    private final EvaluationRunCsvConverter csvConverter;

    public Flux<EvaluationRunDTO> importCsv(InputStream inputStream) {

        try {
            List<EvaluationRunCsv> csvRuns = csvReader.read(inputStream);

            List<EvaluationRun> evaluationRuns = csvRuns.stream()
                    .map(csvConverter::convert)
                    .toList();

            return evaluationRunRepository
                    .saveAll(evaluationRuns)
                    .map(evaluationRunMapper::evalRuntoDTO);

        } catch (IOException e) {
            return Flux.error(e);
        }
    }
}
