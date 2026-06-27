package com.hackthon.service.impl;

import com.hackthon.dto.NotificationDto;
import com.hackthon.modele.Notification;
import com.hackthon.modele.Personnes;
import com.hackthon.repository.CompteUtilisateurRepository;
import com.hackthon.repository.NotificationRepository;
import com.hackthon.repository.PersonneRepository;
import com.hackthon.service.serviceInter.NotificationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private CompteUtilisateurRepository compteUtilisateurRepository;

    @Autowired
    private PersonneRepository personneRepository ;

    @Override
    public NotificationDto createNotification(String message, String type) {
        Personnes user = getConnectedUser();
        Notification notif = new Notification();
        notif.setMessage(message);
        notif.setType(type);
        notif.setLue(false);
        notif.setDate(LocalDateTime.now());
        notif.setUtilisateur(user); // tu peux ajuster selon ton entité
        notificationRepository.save(notif);
        return NotificationDto.fromEntity(notif);
    }

    public NotificationDto Notification(String message, String type, Long id) {
        Personnes user = personneRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "utilisateur non trouver"));
        Notification notif = new Notification();
        notif.setMessage(message);
        notif.setType(type);
        notif.setLue(false);
        notif.setDate(LocalDateTime.now());
        notif.setUtilisateur(user); // tu peux ajuster selon ton entité
        notificationRepository.save(notif);
        return NotificationDto.fromEntity(notif);
    }

    @Override
    public List<NotificationDto> viewAllNotifications() {
        Personnes user = getConnectedUser();
        return notificationRepository.findByUtilisateurIdOrderByDateDesc(user.getId())
                .stream().map(NotificationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<NotificationDto> viewUnreadNotifications() {
        Personnes user = getConnectedUser();
        return notificationRepository.findByUtilisateurIdAndLueFalse(user.getId())
                .stream().map(NotificationDto::fromEntity).collect(Collectors.toList());
    }

    @Override
    public void markAsRead(Long notificationId) {
        Notification notif = notificationRepository.findById(notificationId).orElseThrow();
        notif.setLue(true);
        notificationRepository.save(notif);
    }

    @Override
    public void markAllAsRead() {
        Personnes user = getConnectedUser();
        List<Notification> notifs = notificationRepository.findByUtilisateurIdAndLueFalse(user.getId());
        for (Notification notif : notifs) {
            notif.setLue(true);
        }
        notificationRepository.saveAll(notifs);
    }

    @Override
    public String deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
        return "success";
    }

    // 🔐 Méthode utilitaire pour récupérer l'utilisateur connecté
    private Personnes getConnectedUser() {
        return compteUtilisateurRepository
                .findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"))
                .getPersonne();
    }
}
