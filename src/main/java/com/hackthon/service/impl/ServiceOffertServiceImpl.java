package com.hackthon.service.impl;

import com.hackthon.dto.*;
import com.hackthon.modele.*;
import com.hackthon.repository.CategorieRepository;
import com.hackthon.repository.CompteUtilisateurRepository;
import com.hackthon.repository.ServicePhotoRepository;
import com.hackthon.repository.ServiceRepository;
import com.hackthon.service.serviceInter.ServiceOffertService;
import com.hackthon.utils.FileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static java.awt.geom.Point2D.distance;


@Slf4j
@Service
public class ServiceOffertServiceImpl implements ServiceOffertService {
    private final ServiceRepository serviceRpository;
    private final GeoLocationServiceImpl geoLocationService;
    private final CategorieRepository categorieRepository;
    private  final CompteUtilisateurRepository compteUtilisateurRepository;
    private final FileService fileService;
    private final ServicePhotoRepository servicePhotoRepository;
    @Value("${photo.uploads.profileService}")
    private  String PhotoService_DIRECTORY ;

    public ServiceOffertServiceImpl(ServiceRepository serviceRpository, GeoLocationServiceImpl geoLocationService, CategorieRepository categorieRepository, CompteUtilisateurRepository compteUtilisateurRepository, FileService fileService, ServicePhotoRepository servicePhotoRepository) {
        this.serviceRpository = serviceRpository;
        this.geoLocationService = geoLocationService;
        this.categorieRepository = categorieRepository;
        this.compteUtilisateurRepository = compteUtilisateurRepository;
        this.fileService = fileService;
        this.servicePhotoRepository = servicePhotoRepository;

    }

    @Autowired
    private NotificationServiceImpl notificationService;



    @Override
    public ListeServiceReturnDto findById(Long id) {
        Optional<ServiceOffert> service = serviceRpository.findById(id);
        return service.map(ListeServiceReturnDto::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Aucune entuite trouver"));
    }

    @Override
    public ReturnServiceCreationDto RefuserService(Long idService) {
        ServiceOffert service = serviceRpository.findById(idService)
                .orElseThrow(() -> new EntityNotFoundException("Service introuvable avec ID : " + idService));

        service.setStatus("Refuser");
        serviceRpository.save(service);
        notificationService.createNotification("Votre offre de service  a été refusée. Raison : veuillez consultez votre boite mail. Veuillez la corriger et la soumettre à nouveau.", "Service");

        return ReturnServiceCreationDto.builder()
                .id(service.getId())
                .libelle(service.getLibelle())
                .description(service.getDescription())
                .gps(service.getGps())
                .status(service.getStatus())
                .dateCreation(service.getDateCreation())
                .idVendeur(service.getPrestataire().getId())
                .categorieLibelle(service.getCategories().toString())
                .build();
    }

    @Override
    public ReturnServiceCreationDto ApproveService (Long idService) {
        ServiceOffert service = serviceRpository.findById(idService)
                .orElseThrow(() -> new EntityNotFoundException("Service introuvable avec ID : " + idService));

        service.setStatus("validé");
        serviceRpository.save(service);
        notificationService.createNotification("Félicitations ! Votre offre de service a été validée et est maintenant visible sur la plateforme.", "Service");

        return ReturnServiceCreationDto.builder()
                .id(service.getId())
                .libelle(service.getLibelle())
                .description(service.getDescription())
                .gps(service.getGps())
                .status(service.getStatus())
                .dateCreation(service.getDateCreation())
                .idVendeur(service.getPrestataire().getId())
                .categorieLibelle(service.getCategories().toString())
                .build();
    }




    @Override
    public ReturnServiceCreationDto saveOneService(CreateServiceDto createServiceDto) {

        CompteUtilisateur compteUtilisateur= compteUtilisateurRepository
                .findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));

        if (compteUtilisateur.getPersonne() == null) {
            throw new EntityNotFoundException("Aucune personne liée à ce compte utilisateur");
        }
        Personnes vendeur=compteUtilisateur.getPersonne();


        List<Categorie> categories = createServiceDto.getCategorieLibelles().stream()
                .map(libelle -> {
                    Categorie cat = categorieRepository.findByLibelle(libelle);
                    if (cat == null)
                        throw new EntityNotFoundException("Catégorie non trouvée : " + libelle);
                    return cat;
                })
                .collect(Collectors.toList());


            String filename=fileService.saveImage(createServiceDto.getPhoto());
            ServiceOffert service = new ServiceOffert();
            service.setPrestataire(vendeur);
            service.setLibelle(createServiceDto.getLibelle());
            service.setGps(createServiceDto.getGps());
            service.setContact(createServiceDto.getContact());
            service.setDescription(createServiceDto.getDescription());
            service.setStatus("en attente");
            service.setDateCreation(LocalDateTime.now());
            service.setCategories(categories);
            service.setPhoto(filename);
            serviceRpository.save(service);

        // Gestion de la photo


            ServicePhoto image =new ServicePhoto();
            image.setPhoto(filename);
            image.setDateTime(LocalDateTime.now());
            image.setServiceId(service);
            servicePhotoRepository.save(image);


           notificationService.createNotification("Votre offre de service a bien été soumise et est en cours d'examen par notre équipe.", "Service");
        

        return ReturnServiceCreationDto.builder()
                .id(service.getId())
                .libelle(service.getLibelle())
                .description(service.getDescription())
                .gps(service.getGps())
                .status(service.getStatus())
                .dateCreation(service.getDateCreation())
                .idVendeur(service.getPrestataire().getId())
                .categorieLibelle(service.getCategories().stream()
                        .map(Categorie::getLibelle)
                        .collect(Collectors.joining(", ")))
                .build();
    }


    @Override
    public String modifieService(Long idService, CreateServiceDto createServiceDto) {
        ServiceOffert serviceOffert=serviceRpository.findById(idService).orElseThrow(()->new EntityNotFoundException("aucun service trouver avec ce id"));
        serviceOffert.setLibelle(createServiceDto.getLibelle());
        serviceOffert.setGps(createServiceDto.getGps());
        serviceOffert.setDescription(createServiceDto.getDescription());
        serviceOffert.setCategories(createServiceDto.getCategorieLibelles().stream()
                .map(libelle -> {
                    Categorie cat = categorieRepository.findByLibelle(libelle);
                    if (cat == null)
                        throw new EntityNotFoundException("Catégorie non trouvée : " + libelle);
                    return cat;
                })
                .collect(Collectors.toList()));
        if (createServiceDto.getPhoto()!=null){
            String filename=fileService.saveImage(createServiceDto.getPhoto());

            serviceOffert.setPhoto(filename);
            serviceRpository.save(serviceOffert);
            ServicePhoto image =new ServicePhoto();
            image.setPhoto(filename);
            image.setDateTime(LocalDateTime.now());
            image.setServiceId(serviceOffert);
            servicePhotoRepository.save(image);

        }
        serviceRpository.save(serviceOffert);

        return ("vos modification ont ete pris en compte");
    }


    @Override
    public List<ListeServiceReturnDto> findByLibelle(String libelle) {
        List<ServiceOffert> services = serviceRpository.findByLibelleContainingIgnoreCase(libelle.trim()).orElseThrow(()->new ResponseStatusException(HttpStatusCode.valueOf(404), "aucun service avec cet libelle nas ete trouve"));

        return services.stream()
                .map(ListeServiceReturnDto::fromEntity)
                .collect(Collectors.toList());
    }




    @Override
    public List<ListeServiceReturnDto> findByCategorie(String categorieLibelle) {
        List<ServiceOffert> services=serviceRpository.findByCategories_Libelle(categorieLibelle.trim());

        return services.stream()
                .map(ListeServiceReturnDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ListeServiceReturnDto> listeServices() {

         //ListeServiceReturnDto listeServiceReturnDto;

        return serviceRpository.findAll().stream()
                .map(ListeServiceReturnDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<ListeServiceReturnDto> listeServicesEnAttente() {
        return  serviceRpository.findByStatusOrderByDateCreationDesc("en attente").stream()
                .map(ListeServiceReturnDto::fromEntity)
                .toList();
    }


    @Override
    public void delete(Long id) {
        serviceRpository.deleteById(id);
    }

    @Override
    public ServicePhoto addPhoto(MultipartFile photo, Long serviceId) {
        ServiceOffert serviceOffert= serviceRpository.findById(serviceId).orElseThrow(()-> new RuntimeException("Aucun service avec cet id n'a été trouver"));

        String filename=fileService.saveImage(photo);
        ServicePhoto servicePhoto=new ServicePhoto();
        servicePhoto.setPhoto(filename);
        servicePhoto.setDateTime(LocalDateTime.now());
        servicePhoto.setServiceId(serviceOffert);
        servicePhotoRepository.save(servicePhoto);
        return servicePhoto  ;
    }

    @Override
    public String suppPhoto(Long id) {
        servicePhotoRepository.deleteById(id);
        return "photo supprimer avec succès";
    }

    @Override
    public List<String> allPhoto(Long serviceId) {
        return servicePhotoRepository.findByServiceId_Id(serviceId).stream().map(ServicePhoto::getPhoto).toList();
    }

    @Override
    public Resource getPhoto(String filename) {
       try {
                Path photoPath = Paths.get(PhotoService_DIRECTORY).resolve(filename).normalize();
                Resource resource = new UrlResource(photoPath.toUri());
                System.out.println(resource);
                return resource;


                }
            catch (MalformedURLException e) {
                throw new RuntimeException("Erreur lors du chargement du fichier : " + filename, e);
            }



    }



        @Override
        public List<ListeServiceReturnDto> getNearestArtisans(NearestArtisansDTO nearestArtisansDTO ) {

            double[] coordUser = geoLocationService.extractCoordinates(nearestArtisansDTO.getUserGoogleLink());

            double latUser = coordUser[0];
            double lonUser = coordUser[1];
            List<ListeServiceReturnDto> artisans=findByCategorie(nearestArtisansDTO.getCategorie());
            //double[] coord = extractCoordinates(userGoogleLink);
            return artisans.stream()
                    .sorted(Comparator.comparingDouble(a ->
                            distance(
                                    latUser,
                                    lonUser,
                                    geoLocationService.extractCoordinates(a.getGps())[0],
                                    geoLocationService.extractCoordinates(a.getGps())[1]
                            )))
                    .limit(2)
                    .toList();
        }

}


