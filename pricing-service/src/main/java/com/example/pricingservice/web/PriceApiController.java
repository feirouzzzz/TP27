package com.example.pricingservice.web;

import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api/pricing")
public class PriceApiController {

    @GetMapping("/{id}")
    public double computePrice(
            @PathVariable("id") long bookReference,
            @RequestParam(name = "simulateFailure", defaultValue = "false") boolean simulateFailure) {

        // Échec forcé pour tests (circuit breaker, retry)
        if (simulateFailure) {
            throw new IllegalStateException("Service pricing indisponible (simulation)");
        }

        // Échec aléatoire (~30 %) pour tester la résilience
        int randomValue = ThreadLocalRandom.current().nextInt(0, 100);
        if (randomValue < 30) {
            throw new IllegalStateException("Erreur aléatoire du service pricing");
        }

        // Calcul simple du prix basé sur l'identifiant du livre
        return 45.0 + (bookReference % 8) * 6.0;
    }
}
