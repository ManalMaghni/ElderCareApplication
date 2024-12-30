package com.example.elderhealth.controllers;


import com.example.elderhealth.entities.Medicine;
import com.example.elderhealth.services.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/medicines")
public class MedicineController {

    private final MedicineService medicineService;

    @Autowired
    public MedicineController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @GetMapping
    public List<Medicine> getAllMedicines() {
        return medicineService.getAllMedicines();
    }

    @GetMapping("/{id}")
    public Medicine getMedicineById(@PathVariable Long id) {
        return medicineService.getMedicineById(id);
    }



    @PutMapping("/{id}")
    public Medicine updateMedicine(@PathVariable Long id, @RequestBody Medicine medicine) {
        return medicineService.updateMedicine(id, medicine);
    }

    @DeleteMapping("/{id}")
    public void deleteMedicine(@PathVariable Long id) {
        medicineService.deleteMedicine(id);
    }

    @GetMapping("/users/{userId}/medicines")
    public ResponseEntity<List<Medicine>> getMedicinesByUser(@PathVariable Long userId) {
        List<Medicine> medicines = medicineService.getMedicinesByUser(userId);
        return ResponseEntity.ok(medicines);
    }
    @PostMapping("/user/{userId}/medicines")
    public Medicine createMedicine(@PathVariable Long userId, @RequestBody Medicine medicine) {
        return medicineService.saveMedicineWithUser(medicine, userId);
    }



}