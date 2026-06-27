package com.hackthon.dto;

import com.hackthon.modele.Categorie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategorieDto {
    private Long id;
    private String libelle;
    private boolean active;

    public  static  CategorieDto fromEntity(Categorie categorie){
        if(categorie==null){
            return  null;
        }
        return  CategorieDto.builder()
                .id(categorie.getId())
                .libelle(categorie.getLibelle())
                .active(categorie.isActive())
                .build();
    }


    public  static  Categorie toEntity(CategorieDto categorieDto){

        Categorie categorie= new Categorie();
        categorie.setLibelle(categorieDto.getLibelle());
        categorie.setActive(categorieDto.isActive());

        return  categorie;
    }


}
