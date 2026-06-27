package com.hackthon.repository;


import com.hackthon.modele.ServiceOffert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceOffert, Long> {
    List<ServiceOffert> findByCategories_Libelle(String libelle);

    Optional<List<ServiceOffert> >findByLibelleContainingIgnoreCase(String libelle);
    List<ServiceOffert> findByPrestataire_Id(Long id);
    List<ServiceOffert> findByStatusOrderByDateCreationDesc(String statut);

}
