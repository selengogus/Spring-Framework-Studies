package com.example.reactivemongodemo.csv.csvModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationRunCsv {

    private String model;
    private String dataset;
    private String promptVersion;
    private String parameters;
    private String metrics;
}
