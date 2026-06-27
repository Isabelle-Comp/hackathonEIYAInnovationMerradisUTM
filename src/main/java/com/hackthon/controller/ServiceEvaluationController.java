package com.hackthon.controller;

import com.hackthon.dto.ForEvaluateServiceDto;
import com.hackthon.dto.GetServiceEvaluationDto;
import com.hackthon.service.serviceInter.ServiceEvaluationService;
import com.hackthon.utils.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = Constants.ENDPOINT_EvaluerService)
@Tag(name = "Evaluer Service")
public class ServiceEvaluationController {
    @Autowired
    private final ServiceEvaluationService serviceEvaluationService;

    public ServiceEvaluationController(ServiceEvaluationService serviceEvaluationService) {
        this.serviceEvaluationService = serviceEvaluationService;
    }

    @GetMapping("/{serviceId}")
    private ResponseEntity<List<GetServiceEvaluationDto>> ListeEvaluation(@PathVariable Long serviceId){
        return ResponseEntity.ok(serviceEvaluationService.ServiceNote(serviceId));
    }

    @PostMapping
    private ResponseEntity<GetServiceEvaluationDto> Evaluer (@RequestBody ForEvaluateServiceDto evaluer){

        return ResponseEntity.ok(serviceEvaluationService.evaluer(evaluer));
    }
    @GetMapping("serviceRating/{serviceId}")
    int serviceRating(Long serviceId) {
        return serviceEvaluationService.serviceRating(serviceId);
    }
}
