package com.example.reactivemongodemo.csv.csvReader;

import com.example.reactivemongodemo.csv.csvModel.EvaluationRunCsv;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class EvaluationRunCsvReader {

    public List<EvaluationRunCsv> read(InputStream inputStream) throws IOException {

        try (
                Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                CSVParser parser = CSVFormat.DEFAULT.builder()
                        .setHeader()
                        .setSkipHeaderRecord(true)
                        .get()
                        .parse(reader)
        ) {
            List<EvaluationRunCsv> evaluationRuns = new ArrayList<>();

            for (CSVRecord record : parser) {
                EvaluationRunCsv csv = new EvaluationRunCsv(
                        record.get("model"),
                        record.get("dataset"),
                        record.get("promptVersion"),
                        record.get("parameters"),
                        record.get("metrics")
                );

                evaluationRuns.add(csv);
            }

            return evaluationRuns;
        }
    }
}
