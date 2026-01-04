package com.example.libraryservice.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PricingServiceAdapter {

    private final RestTemplate httpClient;
    private final String pricingEndpoint;

    public PricingServiceAdapter(
            RestTemplate httpClient,
            @Value("${pricing.base-url}") String pricingEndpoint) {
        this.httpClient = httpClient;
        this.pricingEndpoint = pricingEndpoint;
    }

    @Retry(name = "pricing-api")
    @CircuitBreaker(name = "pricing-api", fallbackMethod = "defaultPricing")
    public double loadPrice(long bookReference) {
        String requestUrl = pricingEndpoint + "/api/prices/" + bookReference;
        Double result = httpClient.getForObject(requestUrl, Double.class);
        return result != null ? result : 0.0;
    }

    /**
     * Méthode de secours si le service de pricing est indisponible
     */
    public double defaultPricing(long bookReference, Throwable exception) {
        return 0.0;
    }
}
