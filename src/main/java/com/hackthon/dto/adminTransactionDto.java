package com.hackthon.dto;

import com.hackthon.modele.CompteUtilisateur;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class adminTransactionDto {
    private String id;
    private BigDecimal montant;
    private LocalDateTime date;
    private String statut;
    private String type;
    private CompteUtilisateur compteDebiter;
    private CompteUtilisateur compteCrediter;


}
