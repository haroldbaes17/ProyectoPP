package com.proyectopp.proyectopp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ProyectoPpApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProyectoPpApplication.class, args);
    }

}
