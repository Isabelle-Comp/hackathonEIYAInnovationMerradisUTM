package com.hackthon.utils;

import com.hackthon.security.CustomUserDetails;
import com.hackthon.modele.CompteUtilisateur;
import com.hackthon.repository.CompteUtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private CompteUtilisateurRepository compteUtilisateurRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CompteUtilisateur compte = compteUtilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + email));
        return new CustomUserDetails(compte);
    }
}
