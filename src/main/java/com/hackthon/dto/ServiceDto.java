package com.hackthon.dto;

import com.hackthon.modele.ServiceOffert;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ServiceDto {
    private Long id;
    private String libelle;
    private String description;
    private String gps ;
    private String status;
    /*private String photo;*/

    private List<String> categorieLibelles;
    private Long idCategorie;
    private LocalDateTime dateCreation;
    private Long idVendeur;
    private List<String> photo;

    public static ServiceDto fromEntity(ServiceOffert serviceModel) {
        if (serviceModel == null)
            return null;
        return ServiceDto.builder()
                .id(serviceModel.getId())
                .libelle(serviceModel.getLibelle())
                .description(serviceModel.getDescription())
                .gps(serviceModel.getGps())
//                .categorieLibelle(serviceModel.getCategorie() != null ? serviceModel.getCategorie().getLibelle() : null)
//                .idCategorie(serviceModel.getCategorie().getId())
                .dateCreation(serviceModel.getDateCreation())
                .idVendeur(serviceModel.getPrestataire().getId())
                .status(serviceModel.getStatus())
                .build();

    }

    public static ServiceOffert toEntity(ServiceDto serviceDto) {
        if (serviceDto == null)
            return null;
        ServiceOffert serviceModel = new ServiceOffert();
        serviceModel.setId(serviceDto.getId());
        serviceModel.setLibelle(serviceDto.getLibelle());
        serviceModel.setDescription(serviceDto.getDescription());
        serviceModel.setGps(serviceDto.getGps());
        serviceModel.setStatus(serviceModel.getStatus()!=null ? serviceModel.getStatus() : "En attente");
        serviceModel.setDateCreation(LocalDateTime.now());

        return serviceModel;
    }

}
