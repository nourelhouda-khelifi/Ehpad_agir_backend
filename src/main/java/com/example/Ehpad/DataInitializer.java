package com.example.Ehpad;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.Ehpad.entity.AideSoignant;
import com.example.Ehpad.entity.AppUser;
import com.example.Ehpad.repository.AideSoignantRepository;
import com.example.Ehpad.repository.AppUserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

        private final AideSoignantRepository aideSoignantRepository;
        private final AppUserRepository appUserRepository;
        private final PasswordEncoder passwordEncoder;

        @Value("${app.admin.password}")
        private String adminPassword;

        @Value("${app.infirmiere.password}")
        private String infirmierePassword;

        @Override
        public void run(String... args) {
                // ── Utilisateurs par défaut (idempotent) ──────────────────────────
                if (!appUserRepository.existsByUsername("admin")) {
                        appUserRepository.save(AppUser.builder()
                                        .username("admin")
                                        .password(passwordEncoder.encode(adminPassword))
                                        .email("admin@ehpad-agir.fr")
                                        .nom("Administrateur")
                                        .prenom("EHPAD")
                                        .role("ADMIN")
                                        .actif(true)
                                        .build());
                        log.info("Utilisateur 'admin' créé");
                }

                if (!appUserRepository.existsByUsername("infirmiere")) {
                        appUserRepository.save(AppUser.builder()
                                        .username("infirmiere")
                                        .password(passwordEncoder.encode(infirmierePassword))
                                        .email("infirmiere@ehpad-agir.fr")
                                        .nom("Ben")
                                        .prenom("Foulen")
                                        .role("INFIRMIERE")
                                        .actif(true)
                                        .build());
                        log.info("Utilisateur 'infirmiere' créé");
                }

                // ── Aides-soignants d'exemple (seulement si aucun n'existe) ───────
                // En prod, les vrais soignants sont créés via l'interface d'administration
                if (aideSoignantRepository.count() == 0) {
                        aideSoignantRepository.save(AideSoignant.builder()
                                        .code("SE1").nom("Dupont").prenom("Marie")
                                        .secteur("Étage 1").color("#FF6B6B").actif(true).build());
                        aideSoignantRepository.save(AideSoignant.builder()
                                        .code("SC1").nom("Arnould").prenom("Sophie")
                                        .secteur("Étage 1").color("#4ECDC4").actif(true).build());
                        log.info("Aides-soignants par défaut créés");
                }

                log.info("Initialisation terminée — patients et types de soins gérés par Flyway (V4/V5).");
        }
}
