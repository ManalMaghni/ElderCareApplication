package com.example.elderhealth.controllers;

import com.example.elderhealth.services.BilanService;
import com.example.elderhealth.entities.Bilan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bilans")
public class BilanController {

    private final BilanService bilanService;

    @Autowired
    public BilanController(BilanService bilanService) {
        this.bilanService = bilanService;
    }

    @GetMapping
    public List<Bilan> getAllBilans() {
        return bilanService.getAllBilans();
    }

    @GetMapping("users/{userId}/bilans")

    public ResponseEntity<List<Bilan>> getBilansByUser(@PathVariable Long userId) {
        List<Bilan> bilans = bilanService.getBilansByUser(userId);
        return ResponseEntity.ok(bilans);
    }

    @PostMapping("/users/{userId}")
    public Bilan createBilan(@PathVariable Long userId, @RequestBody Bilan bilan) {
        return bilanService.saveBilanWithUser(bilan, userId);
    }


    @PutMapping("/{id}")
    public Bilan updateBilan(@PathVariable Long id, @RequestBody Bilan bilan) {
        return bilanService.updateBilan(id, bilan);
    }

    @DeleteMapping("/{id}")
    public void deleteBilan(@PathVariable Long id) {
        bilanService.deleteBilan(id);
    }}


