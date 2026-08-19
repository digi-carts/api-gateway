package com.digicart.gateway.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Liveness endpoint used by Cloud Run and operators for <em>api-gateway</em>.
 * Public (no JWT): {@code GET /health} and {@code GET /api/health}.
 */
@RestController
public class HealthController {
    /**
     * Handles {@code GET /health} and {@code GET /api/health}.
     * @return reactive completion value
     */
    @GetMapping(path = {"/health", "/api/health"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Map<String, String>> health() {
        return Mono.just(Map.of("status", "ok", "service", "api-gateway"));
    }
}
