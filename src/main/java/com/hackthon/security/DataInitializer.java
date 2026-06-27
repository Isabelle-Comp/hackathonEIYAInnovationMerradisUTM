package com.hackthon.security;


import com.hackthon.enums.RoleName;
import com.hackthon.modele.CompteUtilisateur;
import com.hackthon.modele.Personnes;
import com.hackthon.repository.CategorieRepository;
import com.hackthon.repository.CompteUtilisateurRepository;
import com.hackthon.repository.PersonneRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CompteUtilisateurRepository compteUtilisateurRepository;
    private final PersonneRepository personneRepository;
    private final CategorieRepository categorieRepository;


    private final PasswordEncoder passwordEncoder;

    public DataInitializer(CompteUtilisateurRepository compteUtilisateurRepository, PersonneRepository personneRepository, CategorieRepository categorieRepository, PasswordEncoder passwordEncoder) {
        this.compteUtilisateurRepository = compteUtilisateurRepository;
        this.personneRepository = personneRepository;
        this.categorieRepository = categorieRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    public void run(String... args) {
        CompteUtilisateur compteUtilisateurSuperAdmin = compteUtilisateurRepository.findByEmail("isabellcomp@gmail.com")
                .orElseGet(() -> {

                    //compteUtilisteur
                    CompteUtilisateur newcompteUtilisateur = new CompteUtilisateur();
                    newcompteUtilisateur.setPassword(passwordEncoder.encode("@isabelle23"));
                    newcompteUtilisateur.setRole(RoleName.SUPERADMIN);
                    newcompteUtilisateur.setEmail("isabellcomp@gmail.com");
                    newcompteUtilisateur.setUsername("IsabelleComp");
                    newcompteUtilisateur.setActif(true);

                    //Personnes

                    Personnes personne = new Personnes();
                    personne.setNom("Compaore");
                    personne.setPrenom("Isabelle");
                    personne.setSexe("F");
                    personne.setAdresse("Karpala, Ouagadougou");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate date = LocalDate.parse("23-03-2003", formatter);
                    personne.setDateNaissance(date);
                    personne.setTelephone("+226 54846498");
                    newcompteUtilisateur.setPersonne(personne);

                    personneRepository.save(personne);

                    compteUtilisateurRepository.save(newcompteUtilisateur);
                    return newcompteUtilisateur;


                });

        CompteUtilisateur compteUtilisateurAdmin = compteUtilisateurRepository.findByEmail("audetiend@gmail.com")
                .orElseGet(() -> {

                    //Compteutilisateur
                    CompteUtilisateur newcompteUtilisateur = new CompteUtilisateur();
                    newcompteUtilisateur.setPassword(passwordEncoder.encode("Aude1234"));
                    newcompteUtilisateur.setRole(RoleName.ADMIN);
                    newcompteUtilisateur.setEmail("audetiend@gmail.com");
                    newcompteUtilisateur.setUsername("AudeAdmin");
                    newcompteUtilisateur.setActif(true);

                    //personnes
                    Personnes personne = new Personnes();
                    personne.setNom("TIENDREBEOGO");
                    personne.setPrenom("Aude");
                    personne.setSexe("F");
                    personne.setAdresse("Karpala, Ouagadougou");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate date = LocalDate.parse("05-06-2004", formatter);
                    personne.setDateNaissance(date);
                    personne.setTelephone("+226 54193081");
                    newcompteUtilisateur.setPersonne(personne);

                    personneRepository.save(personne);

                    compteUtilisateurRepository.save(newcompteUtilisateur);
                    return newcompteUtilisateur;

                });
    }



}
