package com.hackthon.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum RoleName {
    UTILISATEUR("Utilisateur"),
    ADMIN("Admin"),
    SUPERADMIN("Super admin");

    private final String value;


    @JsonValue
    public String getValue() {
        return value;
    }

    RoleName(String value) {
        this.value = value;
    }

    public static RoleName fromString(String texte) {
        for (RoleName roleName: RoleName.values()) {
            if (roleName.value.equalsIgnoreCase(texte)){
                return roleName;
            }
        }
        throw new IllegalArgumentException("Aucun role ne correspond à votre saisie.");
    }

}
