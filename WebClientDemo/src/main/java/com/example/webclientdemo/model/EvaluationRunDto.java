package com.example.webclientdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationRunDto {

    private String id;
    private String model;
    private String dataset;
    private String promptVersion;
    private Map<String, Double> parameters;
    private Map<String, Double> metrics;
}
