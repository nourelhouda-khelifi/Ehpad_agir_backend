package com.example.Ehpad.repository;

import com.example.Ehpad.entity.TypeSoin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeSoinRepository extends JpaRepository<TypeSoin, Long> {
    
    Optional<TypeSoin> findByCode(String code);
}
