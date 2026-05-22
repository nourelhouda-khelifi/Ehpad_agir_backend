package com.example.Ehpad.repository;

import com.example.Ehpad.entity.AideSoignant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AideSoignantRepository extends JpaRepository<AideSoignant, Long> {
    
    Optional<AideSoignant> findByCode(String code);
}
