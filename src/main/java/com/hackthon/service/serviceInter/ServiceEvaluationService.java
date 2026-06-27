package com.hackthon.service.serviceInter;

import com.hackthon.dto.ForEvaluateServiceDto;
import com.hackthon.dto.GetServiceEvaluationDto;

import java.util.List;

public interface ServiceEvaluationService {
    public GetServiceEvaluationDto evaluer(ForEvaluateServiceDto evaluateServiceDto);
    public List<GetServiceEvaluationDto> ServiceNote(Long ServiceId);
    public int predictRating(String comment);
    int serviceRating(Long serviceId);
}
