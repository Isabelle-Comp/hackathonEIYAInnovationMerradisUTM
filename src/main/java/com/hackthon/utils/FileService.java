package com.hackthon.utils;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
@Service("fileUtilService")
public class FileService {

    @Value("${photo.uploads.profileService}")
    private  String IMAGE_DIRECTORY ;

    public String saveImage(MultipartFile imageFile) {
        try {
            // Vérifier si le dossier existe, sinon le créer
            String cleanFileName = getString(imageFile);
            String uniqueFileName = UUID.randomUUID().toString() + "_" + cleanFileName;

            // Chemin complet d'enregistrement
            String filePath = IMAGE_DIRECTORY + uniqueFileName;

            // Enregistrer l'image
            Files.write(Paths.get(filePath), imageFile.getBytes());

            // Retourner seulement le nom du fichier pour l'enregistrement en BD
            return uniqueFileName;
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de l'enregistrement de l'image", e);
        }
    }



    @NotNull
    private String getString(MultipartFile imageFile) {
        File directory = new File(IMAGE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Nettoyer le nom du fichier en supprimant les espaces
        String originalFileName = imageFile.getOriginalFilename();
        if (originalFileName == null || originalFileName.isBlank()) {
            throw new RuntimeException("Nom de fichier invalide");
        }

        // Supprimer les espaces et ajouter un UUID pour l'unicité
        String cleanFileName = originalFileName.replaceAll("\\s+", "_");
        return cleanFileName;
    }
}