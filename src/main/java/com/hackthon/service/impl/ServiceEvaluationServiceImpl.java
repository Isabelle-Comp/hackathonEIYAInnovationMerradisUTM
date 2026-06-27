package com.hackthon.service.impl;

import com.hackthon.dto.ForEvaluateServiceDto;
import com.hackthon.dto.GetServiceEvaluationDto;
import com.hackthon.modele.Personnes;
import com.hackthon.modele.ServiceEvaluation;
import com.hackthon.modele.ServiceOffert;
import com.hackthon.repository.CompteUtilisateurRepository;
import com.hackthon.repository.ServiceEvaluationRepository;
import com.hackthon.repository.ServiceRepository;
import com.hackthon.service.serviceInter.ServiceEvaluationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Slf4j
@Service
@AllArgsConstructor
public class ServiceEvaluationServiceImpl implements ServiceEvaluationService {
    private final List<String> positiveWords = List.of(
            "bon", "excellent", "rapide", "propre",
            "satisfait", "parfait", "professionnel", "top"
    );

    private final List<String> negativeWords = List.of(
            "mauvais", "lent", "sale", "déçu",
            "nul", "problème", "retard", "arnaque"
    );
    @Autowired
    private final CompteUtilisateurRepository compteUtilisateurRepository;
    @Autowired
    private final ServiceRepository serviceRepository;
    @Autowired
    private final NotificationServiceImpl notificationService;
    @Autowired
    private final ServiceEvaluationRepository serviceEvaluationRepository;
    @Override
    public GetServiceEvaluationDto evaluer(ForEvaluateServiceDto evaluateServiceDto) {
        Personnes userConnected = compteUtilisateurRepository
                .findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"))
                .getPersonne();
        ServiceOffert service=serviceRepository.findById(evaluateServiceDto.getServiceId()).orElseThrow(()->new RuntimeException("aucun service n'est trouver avec ce id"));
        ServiceEvaluation evaluer= ServiceEvaluation.builder()
                .commentaire(evaluateServiceDto.getAppreciation())
                .auteur(userConnected)
                .date(LocalDateTime.now())
                .service(service)
                .build();
        serviceEvaluationRepository.save(evaluer);
        notificationService.Notification("votre service a éte noté:" +evaluer.getCommentaire(), "Service", service.getPrestataire().getId() );

        return GetServiceEvaluationDto.fromEntity(evaluer);
    }

    @Override
    public List<GetServiceEvaluationDto> ServiceNote(Long serviceId) {
        List<ServiceEvaluation> mesNotes= serviceEvaluationRepository.findByService_id(serviceId);
        return mesNotes.stream()
                .map(GetServiceEvaluationDto::fromEntity)
                .toList();
    }

    @Override
    public int predictRating(String comment) {

            String text = comment.toLowerCase();

            int score = 0;

            for (String word : positiveWords) {
                if (text.contains(word)) score += 1;
            }

            for (String word : negativeWords) {
                if (text.contains(word)) score -= 1;
            }

            return convertScoreToStars(score);
    }

    @Override
    public int serviceRating(Long serviceId) {
            List<GetServiceEvaluationDto> evaluations = ServiceNote(serviceId)
                    .stream()
                    .toList();

            if (evaluations.isEmpty()) {
                return 0;
            }

            int somme = 0;

            for (GetServiceEvaluationDto evaluation : evaluations) {
                somme += predictRating(evaluation.getAppreciation());
            }

            return Math.round((float) somme / evaluations.size());

    }

    private int convertScoreToStars(int score) {

            if (score <= -2) return 1;
            if (score == -1) return 2;
            if (score == 0) return 3;
            if (score == 1) return 4;
            return 5;
        }



}
