package com.example.reactivemongodemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EvaluationRunDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;
    @NotBlank
    @Size(max = 50)
    private String model;
    @NotBlank
    @Size(max = 50)
    private String dataset;
    @NotBlank
    @Size(max = 50)
    private String promptVersion;
    private Map<String, Double> parameters;
    private Map<String, Double> metrics;

}
