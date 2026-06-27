package com.hackthon.security;

import com.hackthon.modele.CompteUtilisateur;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private final CompteUtilisateur compte;

    public CustomUserDetails(CompteUtilisateur compte) {
        this.compte = compte;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + compte.getRole().name());
    }

    @Override
    public String getPassword() {
        return compte.getPassword();
    }

    @Override
    public String getUsername() {
        return compte.getEmail(); // ou getUsername(), selon ton choix
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return compte.isActif();
    }
}
