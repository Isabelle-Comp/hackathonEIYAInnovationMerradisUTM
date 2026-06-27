package com.hackthon.repository;

import com.hackthon.modele.ServiceEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceEvaluationRepository  extends JpaRepository<ServiceEvaluation, Long> {
    List<ServiceEvaluation> findByService_id(Long ServiceId);
}
