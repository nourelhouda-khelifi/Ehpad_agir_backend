package com.example.Ehpad;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.Ehpad.entity.AideSoignant;
import com.example.Ehpad.entity.AlerteNiveau;
import com.example.Ehpad.entity.AlerteType;
import com.example.Ehpad.entity.ExecutionSoin;
import com.example.Ehpad.entity.Patient;
import com.example.Ehpad.entity.PatientAlert;
import com.example.Ehpad.entity.PatientCategorie;
import com.example.Ehpad.entity.PatientStatut;
import com.example.Ehpad.entity.PlanningStatut;
import com.example.Ehpad.entity.TypeSoin;
import com.example.Ehpad.repository.AideSoignantRepository;
import com.example.Ehpad.repository.ExecutionSoinRepository;
import com.example.Ehpad.repository.PatientAlertRepository;
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
        private final PatientAlertRepository patientAlertRepository;

        public DataInitializer(PatientRepository patientRepository,
                        AideSoignantRepository aideSoignantRepository,
                        TypeSoinRepository typeSoinRepository,
                        ExecutionSoinRepository executionSoinRepository,
                        PatientAlertRepository patientAlertRepository) {
                this.patientRepository = patientRepository;
                this.aideSoignantRepository = aideSoignantRepository;
                this.typeSoinRepository = typeSoinRepository;
                this.executionSoinRepository = executionSoinRepository;
                this.patientAlertRepository = patientAlertRepository;
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

                // Create test alerts
                PatientAlert alert1 = PatientAlert.builder()
                                .patient(patient1)
                                .type(AlerteType.DOUCHE_MANQUANTE)
                                .niveau(AlerteNiveau.MOYEN)
                                .message("Aucune douche planifiée cette semaine")
                                .resolue(false)
                                .build();

                PatientAlert alert2 = PatientAlert.builder()
                                .patient(patient1)
                                .type(AlerteType.PATIENT_ALERTE)
                                .niveau(AlerteNiveau.CRITIQUE)
                                .message("Patient signale une douleur persistante")
                                .resolue(false)
                                .build();

                PatientAlert alert3 = PatientAlert.builder()
                                .patient(patient2)
                                .type(AlerteType.AS_SURCHARGE)
                                .niveau(AlerteNiveau.BAS)
                                .message("Aide-soignant surchargée aujourd'hui")
                                .resolue(false)
                                .build();

                patientAlertRepository.save(alert1);
                patientAlertRepository.save(alert2);
                patientAlertRepository.save(alert3);

                log.info("Created 3 test alerts");

                // Create test ExecutionSoins for dashboard
                LocalDate today = LocalDate.now();
                LocalDate yesterday = today.minusDays(1);
                LocalDate twoDaysAgo = today.minusDays(2);

                // Toilettes pour patient 1
                ExecutionSoin exec1 = ExecutionSoin.builder()
                                .patient(patient1)
                                .aideSoignant(aideSoignant1)
                                .typeSoin(typeSoin1)
                                .dateExecution(today)
                                .heureExecution("08:00")
                                .statut(PlanningStatut.EFFECTUE)
                                .commentaire("Toilette complète sans incident")
                                .build();

                // Douche pour patient 2
                ExecutionSoin exec2 = ExecutionSoin.builder()
                                .patient(patient2)
                                .aideSoignant(aideSoignant2)
                                .typeSoin(typeSoin2)
                                .dateExecution(today)
                                .heureExecution("09:00")
                                .statut(PlanningStatut.EFFECTUE)
                                .commentaire("Douche réalisée sans problème")
                                .build();

                // Aide repas pour patient 3
                ExecutionSoin exec3 = ExecutionSoin.builder()
                                .patient(patient3)
                                .aideSoignant(aideSoignant1)
                                .typeSoin(typeSoin5)
                                .dateExecution(today)
                                .heureExecution("12:00")
                                .statut(PlanningStatut.EFFECTUE)
                                .commentaire("Repas pris correctement")
                                .build();

                // Pansement pour patient 4
                ExecutionSoin exec4 = ExecutionSoin.builder()
                                .patient(patient4)
                                .aideSoignant(aideSoignant2)
                                .typeSoin(typeSoin3)
                                .dateExecution(yesterday)
                                .heureExecution("14:00")
                                .statut(PlanningStatut.EFFECTUE)
                                .commentaire("Pansement changé")
                                .build();

                // Toilette pour patient 5
                ExecutionSoin exec5 = ExecutionSoin.builder()
                                .patient(patient5)
                                .aideSoignant(aideSoignant1)
                                .typeSoin(typeSoin1)
                                .dateExecution(yesterday)
                                .heureExecution("08:30")
                                .statut(PlanningStatut.EFFECTUE)
                                .commentaire("Toilette réalisée")
                                .build();

                // Injection pour patient 1
                ExecutionSoin exec6 = ExecutionSoin.builder()
                                .patient(patient1)
                                .aideSoignant(aideSoignant2)
                                .typeSoin(typeSoin4)
                                .dateExecution(twoDaysAgo)
                                .heureExecution("10:00")
                                .statut(PlanningStatut.EFFECTUE)
                                .commentaire("Injection effectuée")
                                .build();

                executionSoinRepository.save(exec1);
                executionSoinRepository.save(exec2);
                executionSoinRepository.save(exec3);
                executionSoinRepository.save(exec4);
                executionSoinRepository.save(exec5);
                executionSoinRepository.save(exec6);

                log.info("Created 6 test ExecutionSoins");

                log.info("Database initialization completed!");
        }
}
