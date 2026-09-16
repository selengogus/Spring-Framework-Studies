package com.example.reactivemongodemo.csv.csvService;

import reactor.core.publisher.Flux;

import java.io.InputStream;

public interface EvaluationRunCsvService <T> {

    public Flux<T> importCsv(InputStream inputStream);
}
