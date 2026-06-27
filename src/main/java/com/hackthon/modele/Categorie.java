package com.hackthon.modele;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "services")
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "libelle", nullable = false, unique = true)
    private String libelle;
    @Column(name = "isActive", nullable = false)
    private boolean isActive;
    @ManyToMany(mappedBy = "categories")
    private List<ServiceOffert> services;
}
