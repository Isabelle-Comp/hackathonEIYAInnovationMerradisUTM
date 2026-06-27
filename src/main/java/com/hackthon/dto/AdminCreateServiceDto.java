package com.hackthon.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminCreateServiceDto {
    private String libelle;
    private String description;
    @NotNull(message = "Il faut obligatoirement un montant")
    private BigDecimal montant ;
    @NotNull(message = "Il faut obligatoirement l'id de la personne qui offre le service")
    private String username ;
    private MultipartFile photo;
    @NotEmpty(message = "Un service doit avoir au moins une catégorie")
    private List<String> categorieLibelles=new ArrayList<>();
}
