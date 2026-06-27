package com.hackthon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class ForSignalServiceDto {
    private String motif;
    private Long serviceId;
}
