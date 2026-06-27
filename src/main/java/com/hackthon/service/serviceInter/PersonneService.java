package com.hackthon.service.serviceInter;

import com.hackthon.dto.PersonneDto;
import com.hackthon.dto.RegisterDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PersonneService {


    PersonneDto deleteUser(Long id);

    List<RegisterDto> listPersonne();
    RegisterDto updateInfoPersonnel( RegisterDto registerDto);

    PersonneDto updatePhotProfile(MultipartFile photo);

    ResponseEntity<?> getProfilImage();
     RegisterDto profilInfo();
     String deleteAvatar();
}
