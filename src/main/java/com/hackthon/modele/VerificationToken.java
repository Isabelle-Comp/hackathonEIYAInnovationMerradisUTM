package com.hackthon.modele;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private LocalDateTime expiration;

    @ManyToOne
    @JoinColumn(name = "compteUtilisateur")
    private CompteUtilisateur compteUtilisateur;

    public VerificationToken(String token, LocalDateTime localDateTime, CompteUtilisateur utilisateur) {
        this.token = token;
        this.expiration = expiration;
        this.compteUtilisateur = utilisateur;
    }
}
