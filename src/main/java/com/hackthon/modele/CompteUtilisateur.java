package com.hackthon.modele;

import com.hackthon.enums.RoleName;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;


@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"personne"})
public class CompteUtilisateur  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    private RoleName role;
    @Column(name = "actif", nullable = false)
    private boolean actif;
    @OneToOne
    @JoinColumn(name = "personne_id", nullable = true)
    @JsonBackReference
    private Personnes personne;

}
