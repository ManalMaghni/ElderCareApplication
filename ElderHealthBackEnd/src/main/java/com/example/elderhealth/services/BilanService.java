package com.example.elderhealth.services;

import com.example.elderhealth.entities.Bilan;
import com.example.elderhealth.entities.User;
import com.example.elderhealth.repositories.BilanRepository;
import com.example.elderhealth.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BilanService {

    private final BilanRepository bilanRepository;
    private final UserRepository userRepository;


    public BilanService(BilanRepository bilanRepository, UserRepository userRepository) {
        this.bilanRepository = bilanRepository;
        this.userRepository = userRepository;
    }

    public List<Bilan> getAllBilans() {
        return bilanRepository.findAll();
    }

    public Bilan getBilanById(Long id) {
        return bilanRepository.findById(id).orElseThrow(() -> new RuntimeException("Bilan not found"));
    }

    public Bilan saveBilan(Bilan bilan) {
        return bilanRepository.save(bilan);
    }

    public Bilan updateBilan(Long id, Bilan bilan) {
        Bilan existingBilan = getBilanById(id);

        if (bilan.getNomTest() != null) {
            existingBilan.setNomTest(bilan.getNomTest());
        }

        if (bilan.getValeur() != null) {
            existingBilan.setValeur(bilan.getValeur());
        }

        return bilanRepository.save(existingBilan);
    }

    public void deleteBilan(Long id) {
        bilanRepository.deleteById(id);
    }

    // Nouvelle méthode pour récupérer les bilans liés à un utilisateur
    public List<Bilan> getBilansByUser(Long userId) {
        return bilanRepository.findByUserId(userId);


    }
    public Bilan saveBilanWithUser(Bilan bilan, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        bilan.setUser(user);
        return bilanRepository.save(bilan);
    }



}
