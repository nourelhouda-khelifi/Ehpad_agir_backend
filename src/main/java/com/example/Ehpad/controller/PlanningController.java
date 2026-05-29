package com.example.Ehpad.controller;

import com.example.Ehpad.dto.ExecutionSoinDTO;
import com.example.Ehpad.dto.PatientDTO;
import com.example.Ehpad.service.ExecutionSoinService;
import com.example.Ehpad.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/planning")
public class PlanningController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private ExecutionSoinService executionSoinService;

    /**
     * Récupère le planning de la semaine (tous les patients et leurs exécutions)
     * 
     * @param startDate Date de début (optionnel, par défaut aujourd'hui)
     * @return Map avec patients et leurs exécutions
     */
    @GetMapping("/weekly")
    public Map<String, Object> getWeeklyPlanning(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {

        LocalDate finalStartDate = (startDate == null) ? LocalDate.now() : startDate;
        LocalDate finalEndDate = finalStartDate.plusDays(6);

        // Récupérer tous les patients
        List<PatientDTO> patients = patientService.getAllPatients();

        // Récupérer toutes les exécutions de soins pour cette semaine
        List<ExecutionSoinDTO> executions = executionSoinService.getAllExecutionsSoins();

        // Filtrer les exécutions pour la semaine
        List<ExecutionSoinDTO> weekExecutions = executions.stream()
                .filter(exec -> {
                    LocalDate execDate = exec.getDateExecution();
                    return !execDate.isBefore(finalStartDate) && !execDate.isAfter(finalEndDate);
                })
                .collect(Collectors.toList());

        // Construire la réponse
        Map<String, Object> response = new HashMap<>();
        response.put("startDate", finalStartDate);
        response.put("endDate", finalEndDate);
        response.put("patients", patients);
        response.put("executions", weekExecutions);

        // Ajouter les jours de la semaine
        List<Map<String, String>> days = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate date = finalStartDate.plusDays(i);
            Map<String, String> day = new HashMap<>();
            day.put("date", date.toString());
            day.put("dayOfWeek", getDayName(date));
            day.put("dayNumber", String.valueOf(date.getDayOfMonth()));
            day.put("month", getMonthName(date));
            days.add(day);
        }
        response.put("days", days);

        return response;
    }

    /**
     * Récupère les exécutions de soins pour une date spécifique
     */
    @GetMapping("/date/{date}")
    public List<ExecutionSoinDTO> getExecutionsByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<ExecutionSoinDTO> allExecutions = executionSoinService.getAllExecutionsSoins();
        return allExecutions.stream()
                .filter(exec -> exec.getDateExecution().equals(date))
                .collect(Collectors.toList());
    }

    /**
     * Récupère les exécutions d'un patient pour une semaine
     */
    @GetMapping("/patient/{patientId}/weekly")
    public List<ExecutionSoinDTO> getPatientWeeklyPlanning(
            @PathVariable Long patientId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {

        LocalDate finalStartDate = (startDate == null) ? LocalDate.now() : startDate;
        LocalDate finalEndDate = finalStartDate.plusDays(6);

        List<ExecutionSoinDTO> allExecutions = executionSoinService.getAllExecutionsSoins();
        return allExecutions.stream()
                .filter(exec -> exec.getPatientId() != null && exec.getPatientId().equals(patientId))
                .filter(exec -> {
                    LocalDate execDate = exec.getDateExecution();
                    return !execDate.isBefore(finalStartDate) && !execDate.isAfter(finalEndDate);
                })
                .collect(Collectors.toList());
    }

    private String getDayName(LocalDate date) {
        String[] days = { "LUN", "MAR", "MER", "JEU", "VEN", "SAM", "DIM" };
        return days[date.getDayOfWeek().getValue() - 1];
    }

    private String getMonthName(LocalDate date) {
        String[] months = { "JAN", "FÉV", "MAR", "AVR", "MAI", "JUN", "JUI", "AOU", "SEP", "OCT", "NOV", "DÉC" };
        return months[date.getMonthValue() - 1];
    }
}
