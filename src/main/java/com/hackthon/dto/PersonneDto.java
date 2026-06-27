package com.hackthon.dto;

import com.hackthon.modele.Categorie;
import com.hackthon.modele.Personnes;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonneDto {

    private Long id;
    private String nom;
    private String prenom;
    private String sexe;
    private String telephone;
    private String adresse;
    private LocalDate dateNaissance;
    private List<Categorie> competences;
    private boolean compteStatut;



    //private String photoPath;

    public  static PersonneDto fromEntity(Personnes personnes){
        if (personnes==null){
            return null;
        }
        return PersonneDto.builder()
                .id(personnes.getId())
                .nom(personnes.getNom())
                .prenom(personnes.getPrenom())
                .dateNaissance(personnes.getDateNaissance())
                .sexe(personnes.getSexe())
                .telephone(personnes.getTelephone())
                .adresse(personnes.getAdresse())
                .compteStatut(personnes.getCompte().isActif())
                .build();

    }

    public static Personnes toEntity (PersonneDto personneDto){
        if(personneDto==null){
            return null;
        }
        return Personnes.builder()
                .nom(personneDto.getNom())
                .prenom(personneDto.getPrenom())
                .dateNaissance(personneDto.dateNaissance)
                .sexe(personneDto.getSexe())
                .adresse(personneDto.getAdresse())
                .telephone(personneDto.getTelephone())

                .build();

    }


}
