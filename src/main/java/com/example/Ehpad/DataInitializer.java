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

                aideSoignantRepository.save(aideSoignant1);

                log.info("Created 1 care staff member");

                // Create Patients
                Patient patient1 = Patient.builder()
                                .numeroChambre("101")
                                .nom("Michel")
                                .prenom("Albert")
                                .etage(1)
                                .statut(PatientStatut.HOSPITALISE)
                                .categorie(PatientCategorie.CAT1)
                                .profil("Profil 1 - Lucide, ambulant")
                                .tempsToiletteLit(45)
                                .build();

                Patient patient2 = Patient.builder()
                                .numeroChambre("102")
                                .nom("Bernard")
                                .prenom("Marguerite")
                                .etage(1)
                                .statut(PatientStatut.HOSPITALISE)
                                .categorie(PatientCategorie.CAT2)
                                .profil("Profil 2 - Lucide, ambulant")
                                .tempsToiletteLit(50)
                                .build();

                Patient patient3 = Patient.builder()
                                .numeroChambre("201")
                                .nom("Durand")
                                .prenom("Jeanne")
                                .etage(2)
                                .statut(PatientStatut.HOSPITALISE)
                                .categorie(PatientCategorie.CAT3)
                                .profil("Profil 3 - Lucide, ambulant")
                                .tempsToiletteLit(60)
                                .build();

                Patient patient4 = Patient.builder()
                                .numeroChambre("202")
                                .nom("Moreau")
                                .prenom("Robert")
                                .etage(2)
                                .statut(PatientStatut.HOSPITALISE)
                                .categorie(PatientCategorie.CAT4)
                                .profil("Profil 4 - Lucide, ambulant")
                                .tempsToiletteLit(75)
                                .build();

                Patient patient5 = Patient.builder()
                                .numeroChambre("301")
                                .nom("Garcia")
                                .prenom("Luc")
                                .etage(3)
                                .statut(PatientStatut.HOSPITALISE)
                                .categorie(PatientCategorie.CAT1)
                                .profil("Profil 5 - Pertes cogn. - ambulant")
                                .tempsToiletteLit(90)
                                .build();

                Patient patient6 = Patient.builder()
                                .numeroChambre("302")
                                .nom("Rodriguez")
                                .prenom("Marie-Christine")
                                .etage(3)
                                .statut(PatientStatut.HOSPITALISE)
                                .categorie(PatientCategorie.CAT2)
                                .profil("Profil 6 - Lucide, ambulant")
                                .tempsToiletteLit(45)
                                .build();

                patientRepository.save(patient1);
                patientRepository.save(patient2);
                patientRepository.save(patient3);
                patientRepository.save(patient4);
                patientRepository.save(patient5);
                patientRepository.save(patient6);

                log.info("Created 6 patients");

                // Create some Executions Soins (Activities)
                ExecutionSoin execution1 = ExecutionSoin.builder()
                                .patient(patient1)
                                .typeSoin(typeSoin1)
                                .aideSoignant(aideSoignant1)
                                .dateExecution(LocalDate.now())
                                .heureExecution("08:30")
                                .statut(PlanningStatut.EFFECTUE)
                                .commentaire("Toilette terminée sans problème")
                                .build();

                ExecutionSoin execution2 = ExecutionSoin.builder()
                                .patient(patient2)
                                .typeSoin(typeSoin2)
                                .aideSoignant(aideSoignant1)
                                .dateExecution(LocalDate.now())
                                .heureExecution("09:00")
                                .statut(PlanningStatut.EFFECTUE)
                                .commentaire("Douche effectuée")
                                .build();

                ExecutionSoin execution3 = ExecutionSoin.builder()
                                .patient(patient3)
                                .typeSoin(typeSoin4)
                                .aideSoignant(aideSoignant1)
                                .dateExecution(LocalDate.now())
                                .heureExecution("10:15")
                                .statut(PlanningStatut.PLANIFIE)
                                .commentaire("")
                                .build();

                ExecutionSoin execution4 = ExecutionSoin.builder()
                                .patient(patient4)
                                .typeSoin(typeSoin5)
                                .aideSoignant(aideSoignant1)
                                .dateExecution(LocalDate.now())
                                .heureExecution("12:00")
                                .statut(PlanningStatut.EFFECTUE)
                                .commentaire("Aide repas terminée")
                                .build();

                executionSoinRepository.save(execution1);
                executionSoinRepository.save(execution2);
                executionSoinRepository.save(execution3);
                executionSoinRepository.save(execution4);

                log.info("Created 4 care executions");
                log.info("Database initialization completed!");
        }
}
