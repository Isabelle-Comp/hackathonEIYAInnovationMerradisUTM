package com.hackthon.repository;

import com.hackthon.modele.CompteUtilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompteUtilisateurRepository extends JpaRepository<CompteUtilisateur, Long> {
    Optional<CompteUtilisateur> findByEmail(String email);
    Optional<CompteUtilisateur> findByUsername(String usermane);
    boolean existsByEmail(String email);
   CompteUtilisateur findByPersonne_id(Long id);
   @Query(value = "SELECT c FROM CompteUtilisateur c WHERE username =:login OR email =:login")
    Optional<CompteUtilisateur> findByUsernameOrEmail(String login);
}
