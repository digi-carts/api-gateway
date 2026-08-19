package com.digicart.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot entry point for the <em>api-gateway</em> microservice.
 */
@SpringBootApplication
public class ApiGatewayApplication {
    /**
     * Spring Boot process entry point.
     *
     * @param args args
     */
    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }
}
