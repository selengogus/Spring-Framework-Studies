package com.example.reactivemongodemo.controller;

import com.example.reactivemongodemo.model.EvaluationRunDTO;
import com.example.reactivemongodemo.service.EvaluationRunService;
import com.example.reactivemongodemo.web.ApiPaths;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Profile("controller")
@RestController
@RequestMapping(ApiPaths.EVALRUN)
@RequiredArgsConstructor
public class EvaluationRunController {

    private final EvaluationRunService service;

    @GetMapping
    public Flux<EvaluationRunDTO> list(@RequestParam(required = false) String model,
                                       @RequestParam(required = false) String dataset,
                                       @RequestParam(required = false) Double minAccuracy) {
        return service.getEvalRuns(model, dataset, minAccuracy)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @GetMapping("/{id}")
    public Mono<EvaluationRunDTO> get(@PathVariable String id) {
        return service.getEvalRun(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @PostMapping
    public Mono<ResponseEntity<EvaluationRunDTO>> create(@Valid @RequestBody EvaluationRunDTO dto) {
        return service.saveEvalRun(dto)
                .map(saved -> ResponseEntity
                        .created(URI.create(ApiPaths.EVALRUN + "/" + saved.getId()))
                        .body(saved));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono update(@PathVariable String id, @Valid @RequestBody EvaluationRunDTO dto) {
        return service.updateEvalRun(id, dto)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .then();
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity> delete(@PathVariable String id) {
        return service.deleteEvalRun(id)
                .map(deleted -> deleted
                        ? ResponseEntity.noContent().build()
                        : ResponseEntity.notFound().build());
    }
}
