package com.hackthon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfilDto {
    private Long idPersonne;
    private String nom;
    private String prenom;
    private String sexe;
    private String telephone;
    private String adresse;
    private Integer age;
    private String profesion;
    private String username;
    private String email;
}
