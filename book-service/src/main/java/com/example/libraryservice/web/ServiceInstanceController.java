package com.example.libraryservice.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceInstanceController {

    @Value("${server.port}")
    private int port;

    @GetMapping("/api/debug/instance")
    public String info() {
        String host = System.getenv().getOrDefault("HOSTNAME", "local-env");
        return "service=" + host + " port=" + port;
    }
}
