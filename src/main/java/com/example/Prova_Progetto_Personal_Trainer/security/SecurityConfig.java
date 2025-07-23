package com.example.Prova_Progetto_Personal_Trainer.security;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Manteniamo questa annotazione se usi @PreAuthorize sui metodi
public class SecurityConfig {

    private final ApplicationContext applicationContext;

    public SecurityConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin(http -> http.disable()); // Disabilita il form login predefinito
        httpSecurity.csrf(http -> http.disable()); // Disabilita la protezione CSRF (necessario per API stateless)
        httpSecurity.sessionManagement(http -> http.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Sessioni stateless

        // Applica la configurazione CORS definita nel bean corsConfigurationSource()
        httpSecurity.cors(Customizer.withDefaults());

        // Inietta il JwtFilter prima del filtro di autenticazione username/password
        JwtFilter jwtFilter = applicationContext.getBean(JwtFilter.class);
        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        // Configura le regole di autorizzazione per le richieste HTTP
        httpSecurity.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/auth/**").permitAll() // Permette l'accesso a tutti gli endpoint di autenticazione
                .requestMatchers(HttpMethod.POST, "/users").permitAll() // Permette la creazione di nuovi utenti
                // Tutte le altre richieste richiedono che l'utente sia autenticato (abbia un token valido)
                // Non vengono richiesti ruoli specifici qui, solo l'autenticazione
                .anyRequest().authenticated()
        );

        return httpSecurity.build(); // Costruisce e restituisce la catena di filtri di sicurezza
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Bean per la codifica delle password (BCrypt)
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // *** Configurazione CORS molto ampia per debug ***
        corsConfiguration.setAllowedOrigins(List.of("*")); // Consente tutte le origini
        corsConfiguration.setAllowedMethods(List.of("*")); // Consente tutti i metodi HTTP (GET, POST, PUT, DELETE, OPTIONS)
        corsConfiguration.setAllowedHeaders(List.of("*")); // Consente tutti gli header nelle richieste
        corsConfiguration.setExposedHeaders(List.of("Authorization", "X-Auth-Token")); // Espone questi header al frontend
        corsConfiguration.setAllowCredentials(true); // Consente l'invio di credenziali (es. cookie, header Authorization)
        corsConfiguration.setMaxAge(3600L); // Cache per 1 ora per le risposte preflight

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration); // Applica questa configurazione a tutti i percorsi

        return source; // Restituisce la sorgente di configurazione CORS
    }
}