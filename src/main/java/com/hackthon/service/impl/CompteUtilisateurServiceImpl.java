package com.hackthon.service.impl;

import com.hackthon.dto.*;
import com.hackthon.enums.RoleName;
import com.hackthon.modele.CompteUtilisateur;
import com.hackthon.modele.Personnes;
import com.hackthon.repository.CategorieRepository;
import com.hackthon.repository.CompteUtilisateurRepository;
import com.hackthon.repository.PersonneRepository;
import com.hackthon.service.serviceInter.CompteUtilisateurService;
import com.hackthon.utils.EmailService;
import com.hackthon.utils.EmailTokenService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;


@Service
@Data
@AllArgsConstructor
public class CompteUtilisateurServiceImpl implements CompteUtilisateurService {
    private final CompteUtilisateurRepository compteUtilisateurRepository;
    private final PersonneRepository personneRepository;
    private final NotificationServiceImpl notificationService;
    private final PasswordEncoder passwordEncoder;
    private  final EmailService emailService;
    private final EmailTokenService emailTokenService;
    private final CategorieRepository categorieRepository;


    @Override
    public SaveUserDto saveUtilisateur(RegisterDto registerDto) {

        try {


            // 1. Créer la personne
            Personnes personne = new Personnes();
            personne.setNom(registerDto.getNom());
            personne.setPrenom(registerDto.getPrenom());
            personne.setSexe(registerDto.getSexe());
            personne.setAdresse(registerDto.getAdresse());
            personne.setDateNaissance(registerDto.getDateNaissance());
            personne.setTelephone(registerDto.getTelephone());

            System.out.println("Créer la personne");

            // 2. Créer le compteUtilisateur
            CompteUtilisateur compte = new CompteUtilisateur();
            compte.setEmail(registerDto.getEmail());
            compte.setUsername(registerDto.getUsername());
            compte.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            compte.setRole(RoleName.UTILISATEUR);
            compte.setActif(true);
            compte.setPersonne(personne);

//    // 4. Sauvegarder (cascading fait le reste)
            personneRepository.save(personne);
            compteUtilisateurRepository.save(compte);
            System.out.println("personneRepository.save(personne)");

            registerDto.setRoleName(RoleName.UTILISATEUR);
            SaveUserDto savedUser= new SaveUserDto(registerDto.getPassword(), compte);
            return savedUser;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }


    }

    @Override
    public PersonneDto deactivateAccounts(Long id) {
        Personnes personnes= personneRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "aucun utilisateur trouver avec ce id"));
        CompteUtilisateur compteUtilisateur=compteUtilisateurRepository.findByPersonne_id(id);
        compteUtilisateur.setActif(false);
        compteUtilisateurRepository.save(compteUtilisateur);
        return PersonneDto.fromEntity(personnes);
    }

    @Override
    public PersonneDto activateAccounts(Long id) {
        Personnes personnes= personneRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "aucun utilisateur trouver avec ce id"));
        CompteUtilisateur compteUtilisateur=compteUtilisateurRepository.findByPersonne_id(id);
        compteUtilisateur.setActif(true);
        compteUtilisateurRepository.save(compteUtilisateur);
        return PersonneDto.fromEntity(personnes);
    }

    @Override
    public String motPasswordOublier( ForgotPasswordRequestdto forgotPasswordRequestdto) {
        CompteUtilisateur compteUtilisateur= compteUtilisateurRepository.findByEmail(forgotPasswordRequestdto.getEmail()).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        String newPassword = emailTokenService.generatePIN(compteUtilisateur);
        String link= "http://localhost:8081/swagger-ui/index.html#/Users%20accounts/verifyPasswordResetAccess";
        String htmlContent=String.format("""
                <html lang="fr">
                         <head>
                             <meta charset="UTF-8" />
                             <title>SongNaana - Récupération de compte</title>
                
                             <style>
                                 body {
                                     font-family: Arial, sans-serif;
                                     background-color: #f4f4f5;
                                     color: #333;
                                     margin: 0;
                                     padding: 20px;
                                 }
                
                                 .container {
                                     max-width: 600px;
                                     margin: auto;
                                     background: #ffffff;
                                     padding: 30px;
                                     border: 2px solid #0B2C6F;
                                     border-radius: 15px;
                                     box-shadow: 0 2px 8px rgba(0,0,0,.08);
                                 }
                
                                 .logo-box{
                                     text-align:center;
                                     margin-bottom:20px;
                                 }
                
                                 .logo-box img{
                                     width:140px;
                                 }
                
                                 h2{
                                     color:#0B2C6F;
                                     text-align:center;
                                 }
                
                                 .code-pin{
                                     display:inline-block;
                                     background:#EEF4FF;
                                     color:#B11217;
                                     border:2px dashed #0B2C6F;
                                     padding:15px 30px;
                                     font-size:28px;
                                     font-weight:bold;
                                     letter-spacing:6px;
                                     border-radius:10px;
                                     margin:25px 0;
                                 }
                
                                 .button{
                                     display:inline-block;
                                     background:#0B2C6F;
                                     color:#ffffff;
                                     text-decoration:none;
                                     padding:12px 30px;
                                     border-radius:8px;
                                     margin-top:20px;
                                     font-weight:bold;
                                 }
                
                                 .button:hover{
                                     background:#143F96;
                                 }
                
                                 .info{
                                     background:#F8F9FA;
                                     border-left:5px solid #B11217;
                                     padding:15px;
                                     border-radius:6px;
                                     margin:20px 0;
                                 }
                
                                 .footer{
                                     margin-top:35px;
                                     text-align:center;
                                     font-size:12px;
                                     color:#777;
                                 }
                
                                 strong{
                                     color:#B11217;
                                 }
                             </style>
                
                         </head>
                
                         <body>
                
                         <div class="container">
                
                             <div class="logo-box">
                                 <img
                                     src="https://votre-domaine.com/songnaana-logo.png"
                                     alt="Logo SongNaana">
                             </div>
                
                             <h2>Récupération de votre compte</h2>
                
                             <p>Bonjour <strong>%s</strong>,</p>
                
                             <p>
                                 Nous avons reçu une demande de récupération de votre compte
                                 <strong>SongNaana</strong>.
                             </p>
                
                             <div class="info">
                                 Utilisez le code de vérification ci-dessous pour poursuivre la procédure de récupération de votre compte.
                             </div>
                
                             <div style="text-align:center;">
                                 <div class="code-pin">%s</div>
                             </div>
                
                             <p>
                                 Ce code est valable pendant <strong>15 minutes</strong>.
                             </p>
                
                             <p style="text-align:center;">
                                 <a href="%s" class="button">
                                     Vérifier mon code
                                 </a>
                             </p>
                
                             <p>
                                 Si vous n'êtes pas à l'origine de cette demande,
                                 vous pouvez ignorer cet e-mail en toute sécurité.
                                 Aucune modification ne sera apportée à votre compte.
                             </p>
                
                             <p>
                                 Merci de votre confiance.
                             </p>
                
                             <p>
                                 <strong>L'équipe SongNaana</strong><br>
                                 <em>Le savoir-faire local à portée de main.</em>
                             </p>
                
                             <div class="footer">
                                 &copy; %d SongNaana. Tous droits réservés.
                             </div>
                
                         </div>
                
                         </body>
                         </html>
                                    """, compteUtilisateur.getUsername(), newPassword, link, LocalDateTime.now().getYear());;
        emailService.entraideEmailSender(forgotPasswordRequestdto.getEmail(),"Reinitialisation de mot de passe", htmlContent);
        return "Merci de consulter votre boite mail pour la suite";
    }


    @Override
    public String ChangePassword(ChangePasswordDto changePasswordDto) {
        String mail= SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(mail);
        CompteUtilisateur compteUtilisateur= compteUtilisateurRepository.findByEmail(mail).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if(passwordEncoder.matches(changePasswordDto.getAncienComptePassword(), compteUtilisateur.getPassword())){
            if(changePasswordDto.getNouveauComptePassword().matches(changePasswordDto.getConfirmNouveauPassword())){
                compteUtilisateur.setPassword(passwordEncoder.encode(changePasswordDto.getNouveauComptePassword()));
                compteUtilisateurRepository.save(compteUtilisateur);
                notificationService.createNotification("Votre mot de passe a été changer avec succes", "Compte");
                return ("Votre mot de passe a ete modifier avec succes");
            }
            else {
                throw new IllegalArgumentException("vos deux mot de passe doivent etre identique");
            }
        }

        else {
            throw new AccessDeniedException("votre ancien mot de passe est incorrect");
        }

    }

    @Override
    public String RecupererCompte(ResetPasswordDto resetPasswordDto) {

        CompteUtilisateur compteUtilisateur = compteUtilisateurRepository
                .findByEmail(resetPasswordDto.getMail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé"));
        

        if (resetPasswordDto.getNouveauComptePassword().matches(resetPasswordDto.getConfirmNouveauPassword())) {
            compteUtilisateur.setPassword(passwordEncoder.encode(resetPasswordDto.getNouveauComptePassword()));
            compteUtilisateurRepository.save(compteUtilisateur);
            //notificationService.createNotification("Le mot de passe a été réinitialisé avec succès", "Compte");
            return "Votre mot de passe a été modifié avec succès";
        } else {

            throw new IllegalArgumentException("Les deux mots de passe doivent être identiques");
        }
    }
}

