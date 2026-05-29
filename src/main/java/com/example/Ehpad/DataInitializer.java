package com.example.Ehpad;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.Ehpad.entity.AideSoignant;
import com.example.Ehpad.entity.ExecutionSoin;
import com.example.Ehpad.entity.Patient;
import com.example.Ehpad.entity.PatientCategorie;
import com.example.Ehpad.entity.PatientStatut;
import com.example.Ehpad.entity.PlanningStatut;
import com.example.Ehpad.entity.TypeSoin;
import com.example.Ehpad.repository.AideSoignantRepository;
import com.example.Ehpad.repository.ExecutionSoinRepository;
import com.example.Ehpad.repository.PatientRepository;
import com.example.Ehpad.repository.TypeSoinRepository;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

        private final PatientRepository patientRepository;
        private final AideSoignantRepository aideSoignantRepository;
        private final TypeSoinRepository typeSoinRepository;
        private final ExecutionSoinRepository executionSoinRepository;

        public DataInitializer(PatientRepository patientRepository,
                        AideSoignantRepository aideSoignantRepository,
                        TypeSoinRepository typeSoinRepository,
                        ExecutionSoinRepository executionSoinRepository) {
                this.patientRepository = patientRepository;
                this.aideSoignantRepository = aideSoignantRepository;
                this.typeSoinRepository = typeSoinRepository;
                this.executionSoinRepository = executionSoinRepository;
        }

        @Override
        public void run(String... args) throws Exception {
                log.info("Initializing database with test data...");

                // Create Types de Soins
                TypeSoin typeSoin1 = TypeSoin.builder()
                                .code("TOILETTE")
                                .libelle("Toilette")
                                .dureeParDefaut(45)
                                .actif(true)
                                .build();

                TypeSoin typeSoin2 = TypeSoin.builder()
                                .code("DOUCHE")
                                .libelle("Douche")
                                .dureeParDefaut(60)
                                .actif(true)
                                .build();

                TypeSoin typeSoin3 = TypeSoin.builder()
                                .code("PANSEMENT")
                                .libelle("Pansement")
                                .dureeParDefaut(30)
                                .actif(true)
                                .build();

                TypeSoin typeSoin4 = TypeSoin.builder()
                                .code("INJECTION")
                                .libelle("Injection")
                                .dureeParDefaut(15)
                                .actif(true)
                                .build();

                TypeSoin typeSoin5 = TypeSoin.builder()
                                .code("AIDE_REPAS")
                                .libelle("Aide repas")
                                .dureeParDefaut(30)
                                .actif(true)
                                .build();

                typeSoinRepository.save(typeSoin1);
                typeSoinRepository.save(typeSoin2);
                typeSoinRepository.save(typeSoin3);
                typeSoinRepository.save(typeSoin4);
                typeSoinRepository.save(typeSoin5);

                log.info("Created 5 care types");

                // Create Aides Soignants
                AideSoignant aideSoignant1 = AideSoignant.builder()
                                .code("SE1")
                                .nom("Dupont")
                                .prenom("Marie")
                                .secteur("Étage 1")
                                .color("#FF6B6B")
                                .actif(true)
                                .build();

                AideSoignant aideSoignant2 = AideSoignant.builder()
                                .code("SC1")
                                .nom("Arnould")
                                .prenom("Sophie")
                                .secteur("Étage 1")
                                .color("#4ECDC4")
                                .actif(true)
                                .build();

                aideSoignantRepository.save(aideSoignant1);
                aideSoignantRepository.save(aideSoignant2);

                log.info("Created 2 care staff members");

                // Create Patients - matching frontend mockPatients
                Patient patient1 = Patient.builder()
                                .numeroChambre("1er 12 S")
                                .nom("Cormier")
                                .prenom("Elyane")
                                .etage(1)
                                .statut(PatientStatut.HOSPITALISE)
                                .categorie(PatientCategorie.CAT2)
                                .profil("profil4")
                                .tempsToiletteLit(15)
                                .build();

                Patient patient2 = Patient.builder()
                                .numeroChambre("1er 13 S")
                                .nom("Madoire")
                                .prenom("Monique")
                                .etage(1)
                                .statut(PatientStatut.HOSPITALISE)
                                .categorie(PatientCategorie.CAT1)
                                .profil("profil1")
                                .tempsToiletteLit(0)
                                .build();

                Patient patient3 = Patient.builder()
                                .numeroChambre("1er 14 S")
                                .nom("Marysaël")
                                .prenom("Suzanne")
                                .etage(1)
                                .statut(PatientStatut.HOSPITALISE)
                                .categorie(PatientCategorie.CAT1)
                                .profil("profil2")
                                .tempsToiletteLit(0)
                                .build();

                Patient patient4 = Patient.builder()
                                .numeroChambre("1er 15 S")
                                .nom("Boyer")
                                .prenom("Lucienne")
                                .etage(1)
                                .statut(PatientStatut.HOSPITALISE)
                                .categorie(PatientCategorie.CAT3)
                                .profil("profil7")
                                .tempsToiletteLit(10)
                                .build();

                Patient patient5 = Patient.builder()
                                .numeroChambre("1er 16 S")
                                .nom("Lopez")
                                .prenom("Maria")
                                .etage(1)
                                .statut(PatientStatut.HOSPITALISE)
                                .categorie(PatientCategorie.CAT4)
                                .profil("profil11")
                                .tempsToiletteLit(20)
                                .build();

                patientRepository.save(patient1);
                patientRepository.save(patient2);
                patientRepository.save(patient3);
                patientRepository.save(patient4);
                patientRepository.save(patient5);

                log.info("Created 5 patients");

                // Les ExecutionSoins seront créés via l'API frontend
                // (Pas de données de test pour ne pas surcharger SE1)

                log.info("Database initialization completed!");
        }
}
