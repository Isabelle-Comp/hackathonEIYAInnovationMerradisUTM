package com.hackthon.modele;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@ToString(exclude = {"compte", "services"})

@AllArgsConstructor
public class Personnes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nom", nullable = false)
    private String nom;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "prenom", nullable = false)
    private String prenom;
    @Column(name = "sexe", nullable = false)
    private String sexe;
    @Column(name = "dateNaissance")
    private LocalDate dateNaissance;
    @Column(name = "tel")
    private String telephone;
    @Column(name = "adresse")
    private String adresse;
    @OneToOne(mappedBy = "personne", cascade = CascadeType.ALL)
    @JsonManagedReference
    private CompteUtilisateur compte;
    @OneToMany(mappedBy = "prestataire")
    List<ServiceOffert> commerce;
    @OneToMany(mappedBy = "auteur_du_signalement")
    List<SignalementsServices> signalement;

}