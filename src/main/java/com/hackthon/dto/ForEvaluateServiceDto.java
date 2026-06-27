package com.hackthon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ForEvaluateServiceDto {
    private String appreciation;
    private Long serviceId;
}
