package com.skillbridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point of the SkillBridge Spring Boot application.
 * Running this class starts an embedded Tomcat server on port 8080.
 */
@SpringBootApplication
public class SkillBridgeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkillBridgeApplication.class, args);
        System.out.println("SkillBridge backend is running on http://localhost:8080");
    }
}
