package com.proyectopp.proyectopp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //Metodo para la configuracion del spring security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/admin/productos/**")
                        .ignoringRequestMatchers("/registrarse")
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/registrarse", "/css/**", "/js/**", "/images/**", "/img/**", "/vendor/**", "/buscar-producto", "/ver-producto", "/categorias/**", "/mensaje", "/error", "/verificar-cuenta").permitAll() // públicas
                        .requestMatchers("/admin/**").hasRole("ADMIN") // rutas protegidas para ADMIN
                        .anyRequest().authenticated() // cualquier otra ruta requiere autenticación
                )
                .exceptionHandling(exception -> exception
                        // Si el usuario no está autenticado, redirige a "/"
                        .authenticationEntryPoint((request, response, authException) -> response.sendRedirect("/"))
                        // Si el usuario está autenticado pero no tiene permisos (por ejemplo, no es admin), redirige a "/"
                        .accessDeniedHandler((request, response, accessDeniedException) -> response.sendRedirect("/"))
                )
                .formLogin(form -> form.disable())
                .logout(config -> config.logoutUrl("/logout").logoutSuccessUrl("/"))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}