package com.example.reactivemongodemo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "evaluation_runs")
public class EvaluationRun {

    @Id
    private String id;

    private String model;
    private String dataset;
    private String promptVersion;

    private Map<String, Double> parameters;
    private Map<String, Double> metrics;

    @CreatedDate
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    private LocalDateTime lastModifiedDateTime;

    @Version
    private Long version;
}
