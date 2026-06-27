package com.hackthon.service.serviceInter;

import com.hackthon.dto.CategorieDto;

import java.util.List;

public interface CategorieService {

    CategorieDto save(CategorieDto categorieDto);

    List<CategorieDto> saveAll(List<CategorieDto> categorieDtos);

    CategorieDto update(CategorieDto categorieDto, Long id);

    CategorieDto findById(Long id);

    List<CategorieDto> findAll();

    void delete(Long id);
}
