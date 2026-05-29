package com.example.Ehpad.controller;

import com.example.Ehpad.entity.AideSoignantAbsence;
import com.example.Ehpad.repository.AideSoignantAbsenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/absences")
@CrossOrigin(origins = "http://localhost:5173")
public class AideSoignantAbsenceController {

    @Autowired
    private AideSoignantAbsenceRepository absenceRepository;

    /**
     * Récupérer toutes les absences
     */
    @GetMapping
    public ResponseEntity<List<AideSoignantAbsence>> getAll() {
        return ResponseEntity.ok(absenceRepository.findAll());
    }

    /**
     * Récupérer les absences d'un aide-soignant
     */
    @GetMapping("/aide-soignant/{aideSoignantId}")
    public ResponseEntity<List<AideSoignantAbsence>> getByAideSoignant(@PathVariable Long aideSoignantId) {
        return ResponseEntity.ok(absenceRepository.findByAideSoignantId(aideSoignantId));
    }

    /**
     * Récupérer une absence par ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<AideSoignantAbsence> getById(@PathVariable Long id) {
        Optional<AideSoignantAbsence> absence = absenceRepository.findById(id);
        if (absence.isPresent()) {
            return ResponseEntity.ok(absence.get());
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Créer une nouvelle absence
     */
    @PostMapping
    public ResponseEntity<AideSoignantAbsence> create(@RequestBody AideSoignantAbsence absence) {
        absence.setCreatedAt(LocalDateTime.now());
        absence.setUpdatedAt(LocalDateTime.now());
        if (absence.getStatut() == null) {
            absence.setStatut("ACTIVE");
        }
        AideSoignantAbsence saved = absenceRepository.save(absence);
        return ResponseEntity.ok(saved);
    }

    /**
     * Mettre à jour une absence
     */
    @PutMapping("/{id}")
    public ResponseEntity<AideSoignantAbsence> update(@PathVariable Long id,
            @RequestBody AideSoignantAbsence absenceDetails) {
        Optional<AideSoignantAbsence> absence = absenceRepository.findById(id);
        if (absence.isPresent()) {
            AideSoignantAbsence existing = absence.get();
            if (absenceDetails.getDateDebut() != null) {
                existing.setDateDebut(absenceDetails.getDateDebut());
            }
            if (absenceDetails.getDateFin() != null) {
                existing.setDateFin(absenceDetails.getDateFin());
            }
            if (absenceDetails.getRaison() != null) {
                existing.setRaison(absenceDetails.getRaison());
            }
            if (absenceDetails.getStatut() != null) {
                existing.setStatut(absenceDetails.getStatut());
            }
            if (absenceDetails.getCommentaire() != null) {
                existing.setCommentaire(absenceDetails.getCommentaire());
            }
            existing.setUpdatedAt(LocalDateTime.now());
            return ResponseEntity.ok(absenceRepository.save(existing));
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Supprimer une absence
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (absenceRepository.existsById(id)) {
            absenceRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Annuler une absence (soft delete)
     */
    @PostMapping("/{id}/annuler")
    public ResponseEntity<AideSoignantAbsence> annuler(@PathVariable Long id) {
        Optional<AideSoignantAbsence> absence = absenceRepository.findById(id);
        if (absence.isPresent()) {
            AideSoignantAbsence existing = absence.get();
            existing.setStatut("ANNULEE");
            existing.setUpdatedAt(LocalDateTime.now());
            return ResponseEntity.ok(absenceRepository.save(existing));
        }
        return ResponseEntity.notFound().build();
    }
}
