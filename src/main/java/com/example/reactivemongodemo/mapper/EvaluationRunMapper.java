package com.example.reactivemongodemo.mapper;

import com.example.reactivemongodemo.domain.EvaluationRun;
import com.example.reactivemongodemo.model.EvaluationRunDTO;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface EvaluationRunMapper{
    EvaluationRunDTO evalRuntoDTO(EvaluationRun evalRun);
    EvaluationRun DTOtoEvalRun(EvaluationRunDTO dto);
}
