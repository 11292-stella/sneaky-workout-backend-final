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

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Mantieni questa annotazione se usi @PreAuthorize sui metodi
public class SecurityConfig {

    private final ApplicationContext applicationContext;

    public SecurityConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin(http -> http.disable());
        httpSecurity.csrf(http -> http.disable());
        httpSecurity.sessionManagement(http -> http.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Applica la configurazione CORS definita nel bean corsConfigurationSource()
        // Questo dovrebbe essere sufficiente, ma se non funziona, potremmo dover aggiungere CorsFilter esplicitamente.
        httpSecurity.cors(Customizer.withDefaults());

        JwtFilter jwtFilter = applicationContext.getBean(JwtFilter.class);
        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                // Aggiungi qui le regole specifiche per gli endpoint protetti con hasAuthority
                // Assicurati che l'utente abbia l'autorità 'USER' (come nel tuo DB)
                .requestMatchers(HttpMethod.POST, "/save").hasAuthority("USER") // Per salvare schede
                .requestMatchers(HttpMethod.GET, "/save/schede").hasAuthority("USER") // Per visualizzare le schede salvate
                .requestMatchers(HttpMethod.GET, "/prodotti").hasAuthority("USER") // Se anche /prodotti richiede autenticazione
                .anyRequest().authenticated() // Tutte le altre richieste non specificate sopra richiedono solo autenticazione
        );

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // *** MODIFICA QUI PER CONSENTIRE TUTTO (SOLO PER DEBUG) ***
        corsConfiguration.setAllowedOrigins(List.of("*")); // Consentire tutte le origini
        corsConfiguration.setAllowedMethods(List.of("*")); // Consentire tutti i metodi (GET, POST, PUT, DELETE, OPTIONS)
        corsConfiguration.setAllowedHeaders(List.of("*")); // Consentire tutti gli header
        corsConfiguration.setExposedHeaders(List.of("Authorization", "X-Auth-Token")); // Mantenere questi per il frontend
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(3600L); // Cache per 1 ora

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}