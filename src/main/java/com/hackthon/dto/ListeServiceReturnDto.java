package com.hackthon.dto;

import com.hackthon.modele.Categorie;
import com.hackthon.modele.ServiceOffert;
import com.hackthon.repository.CompteUtilisateurRepository;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder

public class ListeServiceReturnDto {

        private Long id;
        private String libelle;
        private String description;
        private String gps ;
        private String Status;
        /*private String photo;*/

        private List<String> categorieLibelles;
        private Long idCategorie;
        private LocalDateTime dateCreation;
        private String vendeurUsername;
        private Long idVendeur;
        private String photo;

        private final CompteUtilisateurRepository compteUtilisateurRepository;

        public static ListeServiceReturnDto fromEntity(ServiceOffert serviceModel) {

            //CompteUtilisateur compteUtilisateurvendeur=compteUtilisateurRepository.findByPersonne_id(serviceModel.getVendeur().getId());

            //String  vendeurUsername=compteUtilisateurvendeur.getUsername();
            return ListeServiceReturnDto.builder()
                    .id(serviceModel.getId())
                    .libelle(serviceModel.getLibelle())
                    .description(serviceModel.getDescription())
                    .gps(serviceModel.getGps())
                    .categorieLibelles(serviceModel.getCategories().stream().map(Categorie::getLibelle).collect(Collectors.toList()))
                    .Status(serviceModel.getStatus())
                    .dateCreation(serviceModel.getDateCreation())
                    .photo(serviceModel.getPhoto())
                    //.vendeurUsername(vendeurUsername)
                    .idVendeur(serviceModel.getPrestataire().getId())
                    .vendeurUsername(serviceModel.getPrestataire().getCompte().getUsername())
                    .build();

        }

    }
