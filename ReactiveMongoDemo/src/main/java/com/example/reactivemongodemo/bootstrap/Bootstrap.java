package com.example.reactivemongodemo.bootstrap;

import com.example.reactivemongodemo.csv.csvService.EvaluationRunCsvService;
import com.example.reactivemongodemo.model.EvaluationRunDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("init")
public class Bootstrap implements CommandLineRunner {

    private final EvaluationRunCsvService<EvaluationRunDTO> csvService;

    @Override
    public void run(String... args) throws Exception {
        csvService.importCsv(new ClassPathResource("csvFiles/EvaluationRunCSV")
                .getInputStream())
                .subscribe(
                        dto -> {},
                        err -> System.out.println("Error: " + err),
                        () -> System.out.println("Bootstrap data load completed")
                );
    }
}
