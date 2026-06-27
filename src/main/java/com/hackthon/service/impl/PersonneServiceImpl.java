package com.hackthon.service.impl;

import com.hackthon.dto.PersonneDto;
import com.hackthon.dto.RegisterDto;
import com.hackthon.modele.CompteUtilisateur;
import com.hackthon.modele.Personnes;
import com.hackthon.repository.CategorieRepository;
import com.hackthon.repository.CompteUtilisateurRepository;
import com.hackthon.repository.PersonneRepository;
import com.hackthon.service.serviceInter.PersonneService;
import com.hackthon.utils.FileService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service

public class PersonneServiceImpl implements PersonneService {
    private final CompteUtilisateurRepository compteUtilisateurRepository;
    private final CategorieRepository categorieRepository;
    private final BCryptPasswordEncoder encoder;
    private final PersonneRepository personneRepository;
    private final FileService fileService;
    @Value("${photo.uploads.profileService}")
    private  String AVATAR_DIRECTORY ;

    // ✅ Constructeur sans AVATAR_DIRECTORY
    public PersonneServiceImpl(
            CompteUtilisateurRepository compteUtilisateurRepository, CategorieRepository categorieRepository,
            BCryptPasswordEncoder encoder,
            PersonneRepository personneRepository,
            FileService fileService
    ) {
        this.compteUtilisateurRepository = compteUtilisateurRepository;
        this.categorieRepository = categorieRepository;
        this.encoder = encoder;
        this.personneRepository = personneRepository;
        this.fileService = fileService;
    }



    @Override
    public PersonneDto deleteUser(Long id) {
        Personnes user = personneRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouve"));

        personneRepository.deleteById(id);
        return PersonneDto.fromEntity(user);
    }

    @Override
    public RegisterDto profilInfo(){

        CompteUtilisateur compteUtilisateur=compteUtilisateurRepository
                .findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));
        Personnes userConnected =compteUtilisateur.getPersonne();
        return RegisterDto.fromEntity(userConnected, compteUtilisateur);
    }

    @Override
    public String deleteAvatar() {
        CompteUtilisateur compteUtilisateur=compteUtilisateurRepository
                .findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));
        Personnes userConnected =compteUtilisateur.getPersonne();
        userConnected.setAvatar(null);
        personneRepository.save(userConnected);
        return "Votre photo a été supprimer avec succes";
    }

    @Override
    public List<RegisterDto> listPersonne() {

        return compteUtilisateurRepository.findAll().stream()
                .map(cu -> RegisterDto.fromEntity(cu.getPersonne(), cu))
                .collect(Collectors.toList());
    }


    @Override
    public RegisterDto updateInfoPersonnel(RegisterDto registerDto) {

        CompteUtilisateur compteUtilisateur=compteUtilisateurRepository
                .findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));
        compteUtilisateur.setUsername(registerDto.getUsername());
        compteUtilisateur.setEmail(registerDto.getEmail());
        compteUtilisateurRepository.save(compteUtilisateur);
        Personnes userToUpdate =compteUtilisateur.getPersonne();

        userToUpdate.setNom(registerDto.getNom());
        userToUpdate.setPrenom(registerDto.getPrenom());
        userToUpdate.setSexe(registerDto.getSexe());
        userToUpdate.setAdresse(registerDto.getAdresse());
        userToUpdate.setDateNaissance(registerDto.getDateNaissance());
        userToUpdate.setTelephone(registerDto.getTelephone());
        personneRepository.save(userToUpdate);
        return RegisterDto.fromEntity(userToUpdate, compteUtilisateur);

    }


    @Override
    public PersonneDto updatePhotProfile(MultipartFile photo) {
        Personnes userConnected = compteUtilisateurRepository
                .findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"))
                .getPersonne();

        if (photo.isEmpty() || userConnected == null) {
            System.out.println("null " + photo.getName() + "   " + userConnected);
            return null;
        } else {
            try {

                String fileName = fileService.saveImage(photo);
                userConnected.setAvatar(fileName);
                System.out.println(fileName);
                personneRepository.save(userConnected);
                return PersonneDto.fromEntity(userConnected);
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }

        }

    }


    @Override
    public ResponseEntity<?> getProfilImage() {
        Personnes userConnected = compteUtilisateurRepository
                .findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"))
                .getPersonne();


        if (userConnected.getAvatar()==null){
            throw new  EntityNotFoundException("Vous n'avez pas enregistrer une photo");
        } else {
            try {
                Path filePath = Paths.get(AVATAR_DIRECTORY).resolve(userConnected.getAvatar());
                Resource resource = new UrlResource(filePath.toUri());

                if (!resource.exists() || !resource.isReadable()) {
                    return null;  // Retourne null si l'image n'existe pas
                }

                return ResponseEntity.ok(resource);
            } catch (Exception e) {
                return null; // Retourne null en cas d'erreur
            }
        }

    }



}
