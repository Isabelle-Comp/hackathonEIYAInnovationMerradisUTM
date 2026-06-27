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
public class Notification {

    @Id
    @GeneratedValue
    private Long id;
    private String message;
    private String type; // nouveau service disponible, administrateur etc.
    private Boolean lue = false;
    private LocalDateTime date;

    @ManyToOne
    private Personnes utilisateur;
}
