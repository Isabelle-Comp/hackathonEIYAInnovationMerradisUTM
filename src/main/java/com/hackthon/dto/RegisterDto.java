package com.hackthon.dto;

import com.hackthon.enums.RoleName;
import com.hackthon.modele.CompteUtilisateur;
import com.hackthon.modele.Personnes;
import com.hackthon.repository.CategorieRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RegisterDto {
        private Long id;
        private String nom;
        private String prenom;
        private String sexe;
        //@Schema(type = "string", pattern = "dd-MM-yyyy", example = "23-03-2003")
        //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        private LocalDate dateNaissance;
        private String telephone;
        private String adresse;
        private RoleName roleName;
        private String email;
        private String username;
        private String password;
        private String avatar;
        private boolean actif;



        public  static RegisterDto fromEntity(Personnes personnes, CompteUtilisateur compteUtilisateur){

                if (personnes==null){
                        return null;
                }

                return RegisterDto.builder()
                        .id(personnes.getId())
                        .nom(personnes.getNom())
                        .prenom(personnes.getPrenom())
                        .dateNaissance(personnes.getDateNaissance())
                        .sexe(personnes.getSexe())
                        .telephone(personnes.getTelephone())
                        .adresse(personnes.getAdresse())
                        .email(compteUtilisateur.getEmail())
                        .roleName(compteUtilisateur.getRole())
                        .username(compteUtilisateur.getUsername())
                        .actif(compteUtilisateur.isActif())
                        .build();

        }

        public static Personnes toPersonne (RegisterDto registerDto){
                CategorieRepository categorieRepository = null;
                if(registerDto==null){
                        return null;
                }
                return Personnes.builder()
                        .nom(registerDto.getNom())
                        .prenom(registerDto.getPrenom())
                        .dateNaissance(registerDto.getDateNaissance())
                        .sexe(registerDto.getSexe())
                        .adresse(registerDto.getAdresse())
                        .telephone(registerDto.getTelephone())
                        .avatar(registerDto.getAvatar())
                        .build();

        }

        public static CompteUtilisateur toCompteUtilisateur (RegisterDto registerDto){
                return CompteUtilisateur.builder()
                        .email(registerDto.getEmail())
                        .role(registerDto.getRoleName())
                        .password(registerDto.getPassword())
                        .username(registerDto.getUsername())
                        .build();
        }

}
