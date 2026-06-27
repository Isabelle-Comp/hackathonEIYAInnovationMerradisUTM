package com.hackthon.security.jwt;


import com.hackthon.modele.CompteUtilisateur;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private Object principal;
    private CompteUtilisateur compteUtilisateur;
    public JwtResponse(Object principal, String token, CompteUtilisateur compteUtilisateur) {
        this.principal = principal;
        this.token = token;
        this.compteUtilisateur = compteUtilisateur;
    }

}
