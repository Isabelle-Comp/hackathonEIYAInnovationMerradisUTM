package com.hackthon.dto;

import com.hackthon.modele.ServiceEvaluation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
@AllArgsConstructor
@Data
@Builder
public class GetServiceEvaluationDto {
    private Long id;
    private String appreciation;
    private LocalDateTime date;
    private Long serviceId;
    private Long IdAuteurSignalement;

    public static GetServiceEvaluationDto fromEntity(ServiceEvaluation serviceEvaluation){
        return GetServiceEvaluationDto.builder()
                .id( serviceEvaluation.getId())
                .appreciation(serviceEvaluation.getCommentaire())
                .IdAuteurSignalement(serviceEvaluation.getAuteur().getId())
                .date(LocalDateTime.now())
                .serviceId(serviceEvaluation.getService().getId())
                .build();
    }
}
