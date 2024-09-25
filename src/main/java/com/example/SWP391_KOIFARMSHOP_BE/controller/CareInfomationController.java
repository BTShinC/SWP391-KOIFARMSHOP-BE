package com.example.SWP391_KOIFARMSHOP_BE.controller;



import com.example.SWP391_KOIFARMSHOP_BE.pojo.CareInfomation;
import com.example.SWP391_KOIFARMSHOP_BE.service.ICareInfomationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/careInfomation")
public class CareInfomationController {
    @Autowired
    private ICareInfomationService iCareInfomationService;
    @GetMapping("/")
    public ResponseEntity<List<CareInfomation>> fetchALlCareInfomation(){
        return ResponseEntity.ok(iCareInfomationService.getAllCareInfomation());
    }
    @PostMapping("/")
    @ResponseStatus (HttpStatus.CREATED)
    public CareInfomation saveCareInfomation( @Valid @RequestBody CareInfomation careInfomation){
        return iCareInfomationService.insertCareInfomation(careInfomation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CareInfomation> updateCareInfomation(@PathVariable long id,@Valid @RequestBody CareInfomation careInfomation){
        CareInfomation updateCareInfomation = iCareInfomationService.updateCareInfomation(id, careInfomation);
        return ResponseEntity.ok(updateCareInfomation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCareInfomation(@PathVariable long id){
        iCareInfomationService.deleteCareInfomation(id);
        return ResponseEntity.ok("Delete Care Infomation success!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<CareInfomation>> getCareInfomationByID(@PathVariable long id){
        Optional<CareInfomation> careInfomation = iCareInfomationService.getCareInfomationByID(id);
        return  ResponseEntity.ok(careInfomation);
    }
}
