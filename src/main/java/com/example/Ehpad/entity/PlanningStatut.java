package com.example.Ehpad.entity;

public enum PlanningStatut {
    PLANIFIE("planifie"),
    EFFECTUE("effectue"),
    ANNULE("annule"),
    REPORTE("reporte");
    
    private final String value;
    
    PlanningStatut(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}
