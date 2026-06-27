package com.hackthon.dto;

import com.hackthon.modele.CompteUtilisateur;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SaveUserDto {
    String comptePassword;
    CompteUtilisateur compte;
}
