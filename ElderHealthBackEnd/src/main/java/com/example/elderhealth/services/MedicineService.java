package com.example.elderhealth.services;

import com.example.elderhealth.entities.Medicine;
import com.example.elderhealth.entities.User;
import com.example.elderhealth.repositories.MedicineRepository;
import com.example.elderhealth.repositories.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineService {

    private final MedicineRepository medicineRepository;
    private final UserRepository userRepository;
    public MedicineService(MedicineRepository medicineRepository,UserRepository userRepository)

    {
        this.medicineRepository = medicineRepository;
        this.userRepository = userRepository;
    }
    public List<Medicine> getMedicinesByUser(Long userId) {
        return medicineRepository.findByUserId(userId);
    }

    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }


    public Medicine getMedicineById(Long id) {
        return medicineRepository.findById(id).orElseThrow(() -> new RuntimeException("Medicine not found"));
    }


    public Medicine updateMedicine(Long id, Medicine medicine) {
        Medicine existingMedicine = getMedicineById(id);

        if (medicine.getNom() != null) {
            existingMedicine.setNom(medicine.getNom());
        }
        if (medicine.getDosage() != null) {
            existingMedicine.setDosage(medicine.getDosage());
        }
        if (medicine.getHeure() != null) {
            existingMedicine.setHeure(medicine.getHeure());
        }
        return medicineRepository.save(existingMedicine);
    }


    public void deleteMedicine(Long id) {
        medicineRepository.deleteById(id);
    }



    public Medicine saveMedicineWithUser(Medicine medicine, Long userId) {
        // Récupérer l'utilisateur
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Associer le médicament à l'utilisateur
        medicine.setUser(user);

        // Sauvegarder le médicament
        return medicineRepository.save(medicine);
    }

}