package com.example.elderhealth.repositories;

import com.example.elderhealth.entities.Bilan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BilanRepository extends JpaRepository<Bilan, Long> {
    List<Bilan> findByUserId(Long userId);
}
