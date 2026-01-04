package com.example.libraryservice.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PricingProvider {

    private final RestTemplate restTemplate;
    private final String pricingApi;

    public PricingProvider(
            RestTemplate restTemplate,
            @Value("${pricing.base-url}") String pricingApi) {
        this.restTemplate = restTemplate;
        this.pricingApi = pricingApi;
    }

    @Retry(name = "pricing-provider")
    @CircuitBreaker(name = "pricing-provider", fallbackMethod = "fallback")
    public double requestPrice(Long bookId) {
        String uri = pricingApi.concat("/api/prices/").concat(bookId.toString());
        Double response = restTemplate.getForObject(uri, Double.class);
        return response == null ? 0.0 : response;
    }

    /**
     * Valeur de secours en cas d'échec du service distant
     */
    public double fallback(Long bookId, Throwable throwable) {
        return 0.0;
    }
}
