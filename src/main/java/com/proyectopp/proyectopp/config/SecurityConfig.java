package com.proyectopp.proyectopp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    //Metodo para la configuracion del spring security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll() // Permitir acceso sin autenticación
                        .anyRequest().authenticated()
                )
                .formLogin().disable() //Deshabilita la redireccion a /login
                .logout().disable(); // Deshabilita el logout si no es necesario

        return http.build();
    }
}
