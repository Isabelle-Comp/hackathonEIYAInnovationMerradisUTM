package com.hackthon.service.impl;

import com.hackthon.dto.CategorieDto;
import com.hackthon.modele.Categorie;
import com.hackthon.repository.CategorieRepository;
import com.hackthon.service.serviceInter.CategorieService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class CategorieServiceImpl implements CategorieService {

    private  final CategorieRepository categorieRepository;

    @Override
    public CategorieDto save(CategorieDto categorieDto) {
        Categorie categorie = CategorieDto.toEntity(categorieDto);
        Categorie savedCategorie = categorieRepository.save(categorie);
        return  CategorieDto.fromEntity(savedCategorie);
    }

    @Override
    public List<CategorieDto> saveAll(List<CategorieDto> categorieDtos) {
        List<Categorie> categories = categorieDtos.stream()
                .map(CategorieDto::toEntity)
                .collect(Collectors.toList());
        categorieRepository.saveAll(categories);
        return  categorieDtos;
    }

    @Override
    public CategorieDto update(CategorieDto categorieDto , Long id) {
         Categorie extingCategorie = categorieRepository.findById(id).
                 orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune donnée avec l'id"+id));

         extingCategorie.setLibelle(categorieDto.getLibelle());
         extingCategorie.setActive(categorieDto.isActive());

         Categorie updatedCategorie = categorieRepository.save(extingCategorie);
         return  CategorieDto.fromEntity(updatedCategorie);

    }

    @Override
    public CategorieDto findById(Long id) {
         Optional<Categorie> categorie = categorieRepository.findById(id);
         return  categorie.map(CategorieDto::fromEntity)
                 .orElseThrow(()->new EntityNotFoundException("Aucune entuite trouver"));
    }

    @Override
    public List<CategorieDto> findAll() {
        return  categorieRepository.findAll().stream()
                .map(CategorieDto::fromEntity)
                .collect(Collectors.toList());

    }

    @Override
    public void delete(Long id) {
        categorieRepository.deleteById(id);
    }
}
