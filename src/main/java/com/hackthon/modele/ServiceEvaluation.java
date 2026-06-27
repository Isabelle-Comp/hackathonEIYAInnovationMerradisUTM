package com.hackthon.modele;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ServiceEvaluation {
    @Id
    @GeneratedValue
    private Long id;
    private String commentaire;
    private int note;//note sur 5
    private LocalDateTime date;
    @ManyToOne
    private ServiceOffert service;
    @ManyToOne
    private Personnes auteur;
}
