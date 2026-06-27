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
public class ServicePhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date", nullable = false)
    private LocalDateTime dateTime;
    @Column(name = "photo", nullable = false)
    private String photo;
    @ManyToOne
    @JoinColumn()
    private ServiceOffert serviceId;
}
