package com.hackthon.controller;


import com.hackthon.dto.*;
import com.hackthon.dto.AdminCreateServiceDto;
import com.hackthon.dto.CreateServiceDto;
import com.hackthon.dto.ListeServiceReturnDto;
import com.hackthon.dto.ReturnServiceCreationDto;
import com.hackthon.modele.CompteUtilisateur;
import com.hackthon.modele.ServiceOffert;
import com.hackthon.modele.ServicePhoto;
import com.hackthon.repository.CompteUtilisateurRepository;
import com.hackthon.utils.EmailService;
import com.hackthon.service.serviceInter.ServiceOffertService;
import com.hackthon.utils.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = Constants.ENDPOINT_SERVICE)
@Tag(name = " Service")
public class ServicesController {

    private final ServiceOffertService serviceOffertService;
    private  final EmailService emailService;
    private final CompteUtilisateurRepository compteUtilisateurRepository;

    @GetMapping("libelle/{libelle}")
    public List<ListeServiceReturnDto> findByLibelle(@PathVariable String libelle){
        return serviceOffertService.findByLibelle(libelle);
    }
    @PatchMapping("/{idService}/validate-offer")
    public ReturnServiceCreationDto ApproveService (@PathVariable Long idService){
        CompteUtilisateur compteUtilisateur= compteUtilisateurRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));
        String htmlContent = String.format("""
                        <html lang="fr">
                               <head>
                                   <meta charset="UTF-8" />
                                   <title>SongNaana - Validation du service</title>
                
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
                
                                       .message{
                                           background:#F8F9FA;
                                           border-left:5px solid #B11217;
                                           padding:15px;
                                           border-radius:6px;
                                           margin:20px 0;
                                       }
                
                                       .footer{
                                           font-size:12px;
                                           color:#777;
                                           margin-top:30px;
                                           text-align:center;
                                       }
                
                                       strong{
                                           color:#B11217;
                                       }
                                   </style>
                
                               </head>
                
                               <body>
                
                               <div class="container">
                
                                   <div class="logo-box">
                                       <img src="https://votre-domaine.com/songnaana-logo.png"
                                            alt="Logo SongNaana">
                                   </div>
                
                                   <h2>🎉 Bonne nouvelle : votre service a été validé !</h2>
                
                                   <p>Bonjour <strong>%s</strong>,</p>
                
                                   <div class="message">
                                       Nous sommes heureux de vous informer que votre <strong>offre de service a été validée</strong>
                                       après vérification par notre équipe.
                                   </div>
                
                                   <p>
                                       Elle est désormais <strong>visible sur la plateforme SongNaana</strong> et accessible à tous les utilisateurs.
                                   </p>
                
                                   <p>
                                       Vous pouvez dès maintenant recevoir des demandes et être contacté directement par les clients intéressés.
                                   </p>
                
                                   <p>
                                       Merci pour votre contribution à la valorisation du savoir-faire local !
                                   </p>
                
                                   <p>
                                       <strong>L’équipe SongNaana</strong><br>
                                       <em>Le savoir-faire local à portée de main.</em>
                                   </p>
                
                                   <div class="footer">
                                       &copy; %d SongNaana. Tous droits réservés.
                                   </div>
                
                               </div>
                
                               </body>
                               </html>
                """, compteUtilisateur.getUsername(), LocalDateTime.now().getYear());

        emailService.entraideEmailSender(compteUtilisateur.getEmail(), "Resultat d'analyse d'offre", htmlContent);
        return serviceOffertService.ApproveService(idService);
    }
    @PatchMapping("/{idService}/deny-offer")
    public ReturnServiceCreationDto RefuserService (@PathVariable Long idService, @RequestBody String raison){
        CompteUtilisateur compteUtilisateur= compteUtilisateurRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));
        String htmlContent = String.format("""
                        <html lang="fr">
                               <head>
                                   <meta charset="UTF-8" />
                                   <title>Publication non validée</title>
                
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
                
                                       .reason{
                                           background:#F8F9FA;
                                           border-left:5px solid #B11217;
                                           padding:15px;
                                           margin:20px 0;
                                           border-radius:6px;
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
                                       <img src="https://votre-domaine.com/songnaana-logo.png"
                                            alt="Logo SongNaana">
                                   </div>
                
                                   <h2>Votre service n'a pas été validé</h2>
                
                                   <p>Bonjour <strong>%s</strong>,</p>
                
                                   <p>
                                       Merci d'avoir proposé votre service sur
                                       <strong>SongNaana</strong>.
                                   </p>
                
                                   <p>
                                       Après vérification par notre équipe de modération,
                                       votre publication n'a malheureusement pas été approuvée
                                       pour le moment.
                                   </p>
                
                                   <div class="reason">
                                       <strong>Motif du refus :</strong><br><br>
                                       %s
                                   </div>
                
                                   <p>
                                       Nous vous invitons à corriger les éléments signalés,
                                       puis à soumettre à nouveau votre publication.
                                   </p>
                
                                   <p>
                                       Notre objectif est de garantir un annuaire fiable,
                                       sécurisé et de qualité pour tous les citoyens.
                                   </p>
                
                                   <p>
                                       Merci pour votre confiance et à bientôt sur
                                       <strong>SongNaana</strong>.
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
                """, compteUtilisateur.getUsername(), raison, LocalDateTime.now().getYear());

        emailService.entraideEmailSender(compteUtilisateur.getEmail(), "Resultat d'analyse d'offre", htmlContent);

        return serviceOffertService.RefuserService(idService);
    }


    @GetMapping("/{id}")
    public ListeServiceReturnDto findById( @PathVariable Long id) {
        return serviceOffertService.findById(id);
    }


    @PostMapping(value = (""), consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> offerService(@Valid @ModelAttribute CreateServiceDto dto) {

        CompteUtilisateur compteUtilisateur= compteUtilisateurRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
               .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));

        String htmlContent = String.format("""
                        <html lang="fr">
                                 <head>
                                   <meta charset="UTF-8" />
                                   <title>Publication de service</title>
                
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
                                       background: white;
                                       padding: 30px;
                                       border: solid 2px #1e3a8a;
                                       border-radius: 15px;
                                       box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
                                     }
                
                                     .logo-box {
                                       text-align: center;
                                       margin-bottom: 20px;
                                     }
                
                                     .footer {
                                       font-size: 12px;
                                       color: #888;
                                       margin-top: 30px;
                                       text-align: center;
                                     }
                
                                     h2 {
                                       color: #1e3a8a;
                                     }
                                   </style>
                                 </head>
                
                                 <body>
                                   <div class="container">
                
                                     <div class="logo-box">
                                       <!-- Logo SongNaana -->
                                       <img
                                         src="/mnt/data/WhatsApp Image 2026-06-27 at 09.13.19(1).jpeg"
                                         alt="Logo SongNaana"
                                         style="width: 120px; margin: 10px"
                                       />
                                     </div>
                
                                     <h2>Votre publication a bien été prise en compte !</h2>
                
                                     <p>Bonjour %s,</p>
                
                                     <p>
                                       Nous avons le plaisir de vous confirmer que votre publication a été enregistrée avec succès
                                       et est actuellement en cours d’évaluation par notre équipe.
                                     </p>
                
                                     <p>
                                       Cette étape nous permet de garantir la qualité et la pertinence des contenus partagés
                                       au sein de la plateforme SongNaana. Une fois l’évaluation terminée, vous recevrez une
                                       notification de validation ou de refus.
                                     </p>
                
                                     <p>Merci pour votre contribution et votre engagement !</p>
                
                                     <p>À très bientôt,</p>
                
                                     <p><strong>L’équipe SongNaana.</strong></p>
                
                                     <div class="footer">
                                       &copy; %d SongNaana. Tous droits réservés.
                                     </div>
                
                                   </div>
                                 </body>
                               </html>
                """, compteUtilisateur.getUsername(), LocalDateTime.now().getYear());
        emailService.entraideEmailSender(compteUtilisateur.getEmail(), "confirmation de reception d'offre", htmlContent);


        ReturnServiceCreationDto Return = serviceOffertService.saveOneService(dto);

        return ResponseEntity.ok(Return);

    }

    @PostMapping(value = ("/add photo"), consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ServicePhoto addPhoto (@RequestBody Long serviceId, @ModelAttribute MultipartFile photo){
        return serviceOffertService.addPhoto(photo, serviceId);
    }


    @GetMapping
    public List<ListeServiceReturnDto> findAll() {
        return serviceOffertService.listeServices();
    }

    @GetMapping("/waiting_validate")
    public List<ListeServiceReturnDto> findWaitingService () {
        return serviceOffertService.listeServicesEnAttente();
    }


    @GetMapping("categorie/{categorielibelle}")
    public List<ListeServiceReturnDto> findByCategorie(@PathVariable String categorielibelle) {
        return serviceOffertService.findByCategorie(categorielibelle);
    }


    @DeleteMapping("/{id}")
    public void deleteService(@PathVariable Long id) {
        serviceOffertService.delete(id);

    }

    @DeleteMapping("/Photo/{photoId}")
    public void deletePhoto(@PathVariable Long photoId) {
        serviceOffertService.suppPhoto(photoId);

    }


    @GetMapping(value = "/display-photos/{filename}", produces = MediaType.IMAGE_JPEG_VALUE)
    public Resource getProfilImage(@PathVariable  String filename){
        return serviceOffertService.getPhoto(filename);
    }

    @GetMapping(value = "/galerie/filename")
    public List<String> allPhotoFilename(Long serviceId){
        return serviceOffertService.allPhoto(serviceId);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    public ResponseEntity<String> modifyService(@PathVariable Long id, @ModelAttribute CreateServiceDto createServiceDto){
        return ResponseEntity.ok(serviceOffertService.modifieService(id, createServiceDto));
    }



    @PostMapping("/nearest")
    public List<ListeServiceReturnDto> getNearestArtisans(NearestArtisansDTO nearestArtisansDTO ){
        return serviceOffertService.getNearestArtisans(nearestArtisansDTO);
    }



}
