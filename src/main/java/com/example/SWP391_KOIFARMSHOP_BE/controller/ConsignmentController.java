package com.example.SWP391_KOIFARMSHOP_BE.controller;

import com.example.SWP391_KOIFARMSHOP_BE.pojo.Consignment;
import com.example.SWP391_KOIFARMSHOP_BE.service.IConsignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@CrossOrigin
@RequestMapping("/api/consignment")
public class ConsignmentController {
    @Autowired
    private IConsignmentService iConsignmentService;
    @GetMapping("/")
    public ResponseEntity<List<Consignment>> fetchALlConsignment(){
        return ResponseEntity.ok(iConsignmentService.getAllConsignment());
    }
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Consignment saveConsignment(@RequestBody Consignment consignment){
        return iConsignmentService.insertConsignment(consignment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consignment> updateConsignment(@PathVariable long id, @RequestBody Consignment consignment){
        Consignment updateConsignment = iConsignmentService.updateConsignment(id, consignment);
        return ResponseEntity.ok(updateConsignment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteConsignment(@PathVariable long id){
        iConsignmentService.deleteConsignment(id);
        return ResponseEntity.ok("Delete Consignment success!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Consignment>> getConsignmentByID(@PathVariable long id){
        Optional<Consignment> consignment = iConsignmentService.getConsignmentByID(id);
        return  ResponseEntity.ok(consignment);
    }
}
