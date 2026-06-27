package com.hackthon.utils;

import com.hackthon.modele.CompteUtilisateur;
import com.hackthon.modele.VerificationToken;
import com.hackthon.repository.CompteUtilisateurRepository;
import com.hackthon.repository.EmailTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class EmailTokenService {
    @Autowired
    private final EmailTokenRepository tokenRepository;
    private final CompteUtilisateurRepository utilisateurRepository;

    public String generateToken(CompteUtilisateur utilisateur) {
        String token = UUID.randomUUID().toString().replace("-", "");
        VerificationToken vt = VerificationToken.builder()
                .token(token)
                .expiration(LocalDateTime.now().plusDays(1))
                .compteUtilisateur(utilisateur)
                .build();
        tokenRepository.save(vt);
        return token;
    }
    public String generatePIN(CompteUtilisateur utilisateur) {
        int code = (int)(Math.random() * 900000) + 100000;
        String token= String.valueOf(code);
        VerificationToken vt = VerificationToken.builder()
                .token(token)
                .expiration(LocalDateTime.now().plusDays(1))
                .compteUtilisateur(utilisateur)
                .build();
        tokenRepository.save(vt);
        return token;
    }

    public void verifyTokenEmail(String token) {

        VerificationToken vt = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token invalide"));

        if (vt.getExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expiré");
        }

        CompteUtilisateur user = vt.getCompteUtilisateur();
        user.setActif(true);
        utilisateurRepository.save(user);
        tokenRepository.delete(vt); // Supprimer le token une fois activé
    }

    public String verifyTokenPassword(String token) {

        VerificationToken vt = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token invalide"));

        if (vt.getExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expiré");
        }

        CompteUtilisateur user = vt.getCompteUtilisateur();
        tokenRepository.delete(vt);
        // Supprimer le token une fois activé
        return user.getEmail();
    }

}
