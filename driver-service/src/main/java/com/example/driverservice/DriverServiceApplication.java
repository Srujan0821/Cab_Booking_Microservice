package com.example.driverservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {
        "com.commonlib.util", // Shared utilities
        "com.example.driverservice" // Driver-service specific packages
})
public class DriverServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DriverServiceApplication.class, args);
        System.out.println("Driver Service is running...");
    }
}
