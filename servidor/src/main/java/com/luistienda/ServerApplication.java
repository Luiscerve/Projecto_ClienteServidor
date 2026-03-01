package com.luistienda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal del servidor Spring Boot.
 * Inicia la aplicación y muestra información del servidor.
 */
@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
        
        System.out.println();
        System.out.println("SERVIDOR TIENDA LUIS");
        System.out.println("Puerto: 12345");
        System.out.println("Swagger: http://localhost:12345/swagger-ui.html");
    }
}
