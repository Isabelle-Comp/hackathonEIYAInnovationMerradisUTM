package com.hackthon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnServiceCreationDto {
    private Long id;
    private String libelle;
    private String gps;
    private String description;
    /*private String photo;*/
    private String status;
    private String categorieLibelle;
    private LocalDateTime dateCreation;
    private Long idVendeur;
}
