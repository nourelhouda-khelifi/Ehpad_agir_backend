package com.example.Ehpad.entity;

public enum SoinMoment {
    MATIN("matin"),
    SOIR("soir"),
    MIDI("midi"),
    NUIT("nuit");
    
    private final String value;
    
    SoinMoment(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
}
