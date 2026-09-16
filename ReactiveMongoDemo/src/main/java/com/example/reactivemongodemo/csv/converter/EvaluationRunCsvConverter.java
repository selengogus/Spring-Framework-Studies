package com.example.reactivemongodemo.csv.converter;

import com.example.reactivemongodemo.csv.csvModel.EvaluationRunCsv;
import com.example.reactivemongodemo.domain.EvaluationRun;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EvaluationRunCsvConverter {

    public EvaluationRun convert(EvaluationRunCsv csv) {
        return EvaluationRun.builder()
                .model(csv.getModel())
                .dataset(csv.getDataset())
                .promptVersion(csv.getPromptVersion())
                .parameters(parseMap(csv.getParameters()))
                .metrics(parseMap(csv.getMetrics()))
                .build();
    }

    private Map<String, Double> parseMap(String value) {
        if (value == null || value.isBlank()) {
            return Map.of();
        }

        return Arrays.stream(value.split(";"))
                .map(entry -> entry.split("="))
                .collect(Collectors.toMap(
                        entry -> entry[0],
                        entry -> Double.parseDouble(entry[1])
                ));
    }
}
