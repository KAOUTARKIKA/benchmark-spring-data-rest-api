package com.tp.benchmarkspringdatarestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BenchmarkSpringDataRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BenchmarkSpringDataRestApiApplication.class, args);
        System.out.println("========================================");
        System.out.println("Spring Data REST app started!");
        System.out.println("========================================");
        System.out.println("Custom endpoints: http://0.0.0.0:8080/");
        System.out.println("  - GET  /categories?page=0&size=10");
        System.out.println("  - GET  /items?page=0&size=10");
        System.out.println("  - GET  /items?categoryId=1&page=0&size=10");
        System.out.println("  - GET  /categories/{id}/items?page=0&size=10");
        System.out.println();
        System.out.println("Spring Data REST endpoints (HAL):");
        System.out.println("  - GET  /categories");
        System.out.println("  - GET  /items");
        System.out.println("  - GET  /items/search/by-category?categoryId=1");
        System.out.println();
        System.out.println("Prometheus metrics: http://0.0.0.0:9404/actuator/prometheus");
        System.out.println("========================================");
    }
}