package com.hackthon.repository;

import com.hackthon.modele.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUtilisateurIdAndLueFalse(Long utilisateurId);
    List<Notification> findByUtilisateurIdOrderByDateDesc(Long id);
}
