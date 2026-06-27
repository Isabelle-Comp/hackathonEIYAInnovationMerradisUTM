package com.hackthon.service.serviceInter;

import com.hackthon.dto.*;
import com.hackthon.modele.ServiceOffert;
import com.hackthon.modele.ServicePhoto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ServiceOffertService {
    ReturnServiceCreationDto RefuserService(Long idService);

    ReturnServiceCreationDto ApproveService(Long idService);

//    List<ReturnServiceCreationDto> saveManyService(List<CreateServiceDto> createServiceDtos);
    ReturnServiceCreationDto saveOneService(CreateServiceDto createServiceDto);

    String  modifieService(Long idService, CreateServiceDto createServiceDto);


    List<ListeServiceReturnDto>findByLibelle(String Libelle);


    List<ListeServiceReturnDto> findByCategorie(String categorieLibelle);
    //List<FindServiceByStatutDto> findByStatut(String statut);

    ListeServiceReturnDto findById(Long id);

    List<ListeServiceReturnDto> listeServices();

    List<ListeServiceReturnDto> listeServicesEnAttente();

    void delete(Long id);

    ServicePhoto addPhoto(MultipartFile photo, Long serviceId);
    String suppPhoto(Long id);
    List<String> allPhoto(Long serviceId);
   Resource getPhoto (String filename);
    List<ListeServiceReturnDto> getNearestArtisans(NearestArtisansDTO nearestArtisansDTO);

    //ReturnServiceCreationDto analyserService()
}
