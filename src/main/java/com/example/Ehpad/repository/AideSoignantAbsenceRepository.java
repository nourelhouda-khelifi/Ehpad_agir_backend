package com.example.Ehpad.repository;

import com.example.Ehpad.entity.AideSoignantAbsence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AideSoignantAbsenceRepository extends JpaRepository<AideSoignantAbsence, Long> {

    /**
     * Récupérer les absences d'un aide-soignant
     */
    List<AideSoignantAbsence> findByAideSoignantId(Long aideSoignantId);

    /**
     * Récupérer les absences actives d'un aide-soignant
     */
    List<AideSoignantAbsence> findByAideSoignantIdAndStatut(Long aideSoignantId, String statut);

    /**
     * Vérifier si un AS est en absence à une date donnée
     */
    List<AideSoignantAbsence> findByAideSoignantIdAndDateDebutLessThanEqualAndDateFinGreaterThanEqualAndStatut(
            Long aideSoignantId, LocalDate dateDebut, LocalDate dateFin, String statut);
}
