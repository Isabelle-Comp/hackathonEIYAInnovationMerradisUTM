package com.hackthon.modele;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"vendeur", "categories"})
public class ServiceOffert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "libelle", nullable = false)
    private String libelle;
    @Column(nullable = true, columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false)
    private String photo;
    @Column(name = "gps", nullable = false)
    private String gps;
    @Column(nullable = false)
    private String contact;
    /*@Column(name = "photo", nullable = false, unique = true)
    private String photo;*/
    @Column(name = "status",nullable = false)
    private String status="En attente";
    @Column(name = "date", nullable = false)
    private LocalDateTime dateCreation ;
    @ManyToOne
    @JoinColumn(name = "prestataire")
    private Personnes prestataire;
    @NotEmpty(message = "Le service doit avoir au moins une catégorie")
    @ManyToMany
    @JoinTable(
            name = "categorie_service",
            joinColumns = @JoinColumn(name = "service_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "categorie_id", nullable = false)
    )
    private List<Categorie> categories;

    @OneToMany(mappedBy = "serviceId", cascade = CascadeType.REMOVE, orphanRemoval = true)
    public List<ServicePhoto> servicePhotos;
    @OneToMany(mappedBy = "service")
    List<SignalementsServices> signalement;



}
