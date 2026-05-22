package com.example.Ehpad.repository;

import com.example.Ehpad.entity.Patient;
import com.example.Ehpad.entity.PatientCategorie;
import com.example.Ehpad.entity.PatientStatut;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    
    List<Patient> findByNumeroChambreContainingIgnoreCase(String numeroChambre);
    Optional<Patient> findByNumeroChambre(String numeroChambre);
    
    List<Patient> findByEtage(Integer etage);
    
    List<Patient> findByStatut(PatientStatut statut);
    
    List<Patient> findByCategorie(PatientCategorie categorie);
}
