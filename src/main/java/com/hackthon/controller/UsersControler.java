package com.hackthon.controller;


import com.hackthon.dto.PersonneDto;
import com.hackthon.dto.RegisterDto;
import com.hackthon.service.serviceInter.PersonneService;
import com.hackthon.utils.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = Constants.ENDPOINT_User)
@Slf4j
@Tag(name = "Users")
public class UsersControler {

    private final PersonneService personneService;
    @GetMapping("/connect")
    public RegisterDto ProfilInfo() {
        return personneService.profilInfo();
    }

    @GetMapping("")
    public ResponseEntity<List<RegisterDto>> listePersonne() {
        return ResponseEntity.ok(personneService.listPersonne());
    }
    @PatchMapping("/personnal-information")
    public ResponseEntity <RegisterDto> updateInfoPersonnel( @RequestBody RegisterDto registerDto){
        return ResponseEntity.ok(personneService.updateInfoPersonnel( registerDto)) ;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PersonneDto>  deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(personneService.deleteUser(id)) ;
    }

    @PutMapping(value = "/change-avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PersonneDto>  updatePhotProfile(@RequestParam MultipartFile photo) {
        return ResponseEntity.ok(personneService.updatePhotProfile(photo)) ;
    }

    @PutMapping(value = "/delete-avatar")
    public ResponseEntity<String>  updatePhotProfile() {
        return ResponseEntity.ok(personneService.deleteAvatar()) ;
    }

    @GetMapping(value = "/avatar", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getProfilImage(){
        return personneService.getProfilImage();
    }


}
