package com.example.SWP391_KOIFARMSHOP_BE.controller;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.CarePackage;
import com.example.SWP391_KOIFARMSHOP_BE.service.CarePackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/carePackage")
public class CarePackageController {
    @Autowired
    private CarePackageService carePackageService;
    @GetMapping("/")
    public ResponseEntity<List<CarePackage>> fetchALlCarePackage(){
        return ResponseEntity.ok(carePackageService.getAllCarePackage());
    }
    @PostMapping("/")
    @ResponseStatus (HttpStatus.CREATED)
    public CarePackage saveCarePackage(@RequestBody CarePackage carePackage){
        return carePackageService.insertCarePackage(carePackage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarePackage> updateCarePackage(@PathVariable long id, @RequestBody CarePackage carePackage){
        CarePackage updateCarePackage = carePackageService.updateCarePackage(id, carePackage);
        return ResponseEntity.ok(updateCarePackage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCarePackage(@PathVariable long id){
        carePackageService.deleteCarePackage(id);
        return ResponseEntity.ok("Delete Care Package success!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CarePackage>> getCarePackageByID(@PathVariable long id){
        Optional<CarePackage> carePackage = carePackageService.getCarePackageByID(id);
        return  ResponseEntity.ok(carePackage);
    }
}
