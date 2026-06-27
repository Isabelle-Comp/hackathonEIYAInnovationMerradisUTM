package com.hackthon.controller;


import com.hackthon.dto.CategorieDto;
import com.hackthon.service.serviceInter.CategorieService;
import com.hackthon.utils.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = Constants.ENDPOINT_CATEGORIE)
@Tag(name = "Service categories")
public class CategoriesController {
    private  final CategorieService categorieService;
    @Autowired
    public CategoriesController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @PostMapping
    public ResponseEntity<?> addCategories(@RequestBody CategorieDto dto){
        return ResponseEntity.ok(categorieService.save(dto));
    }

    @PutMapping("/{id}")
    public CategorieDto update(@RequestBody CategorieDto categorieDto, @PathVariable Long id){
        return  categorieService.update(categorieDto, id);
    }

    @GetMapping("/{id}")
    public CategorieDto findById(@PathVariable Long id){
        return  categorieService.findById(id);
    }

    @GetMapping()
    public List<CategorieDto> findAll(){
       return  categorieService.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
       categorieService.delete(id);
    }

}
