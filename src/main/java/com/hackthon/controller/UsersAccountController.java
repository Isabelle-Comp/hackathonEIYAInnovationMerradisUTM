package com.hackthon.controller;

//import com.essitech.entraide.dto.*;
import com.hackthon.dto.*;
import com.hackthon.modele.CompteUtilisateur;
import com.hackthon.repository.CompteUtilisateurRepository;
import com.hackthon.utils.EmailService;
import com.hackthon.utils.EmailTokenService;
import com.hackthon.service.serviceInter.CompteUtilisateurService;
import com.hackthon.utils.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = Constants.ENDPOINT_CompteUtilisateur)
@Slf4j
@Tag(name = "Users accounts")
public class UsersAccountController {
    private final CompteUtilisateurService compteUtilisateurService;
    private final EmailTokenService emailTokenService;
    private  final EmailService emailService;
    private  final CompteUtilisateurRepository compteUtilisateurRepository;

    @PostMapping(value = "/register")
    public ResponseEntity<?> Register(@RequestBody RegisterDto registerDto){
        // 1. Enregistre l'utilisateur et retourne l'entité
        SaveUserDto savedUtilisateur = compteUtilisateurService.saveUtilisateur(registerDto);

        // 5. Retourne un message
        return ResponseEntity.ok("Inscription réussie. veillez vous connecter pour proposer votre offre.");

    }


    @PatchMapping(value ="/change-password" )
    String changePassword(@RequestBody ChangePasswordDto changePasswordDto){
        return compteUtilisateurService.ChangePassword(changePasswordDto);
    }
    @PostMapping(value ="/forgot-password" )
    String changePassword(@RequestBody ForgotPasswordRequestdto forgotPasswordRequestdto){
        return compteUtilisateurService.motPasswordOublier(forgotPasswordRequestdto);
    }

    @GetMapping(value ="/Verify-Password-reset-access/{PIN}" )
    String verifyPasswordResetAccess(@PathVariable String PIN){
        return emailTokenService.verifyTokenPassword(PIN);
    }
    @PatchMapping(value ="/reset-password")
    String changePassword(@RequestBody ResetPasswordDto resetPasswordDto){
       return compteUtilisateurService.RecupererCompte(resetPasswordDto);
    }


}
