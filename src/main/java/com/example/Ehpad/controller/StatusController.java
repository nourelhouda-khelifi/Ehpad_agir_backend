package com.example.Ehpad.controller;

import java.time.Instant;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class StatusController {

    @GetMapping("/ping")
    public PingResponse ping() {
        return new PingResponse("pong", Instant.now().toString());
    }

    public record PingResponse(String message, String timestamp) {
    }
}