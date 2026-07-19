package com.sanctuary.sih.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // ========== ROUTES PUBLIQUES (sans authentification) ==========

                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/definir-mot-de-passe").permitAll()
                        .requestMatchers("/api/auth/forgot-password").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        // ========== CRÉATION DE COMPTE (admin uniquement) ==========
                        .requestMatchers(HttpMethod.POST, "/api/auth/register").hasRole("ADMIN")

                        // ========== LECTURE (tous les rôles connectés) ==========
                        .requestMatchers(HttpMethod.GET, "/api/medical/**").hasAnyRole("MEDECIN", "AGENT_ADMISSION", "COMPTABLE", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/admission/patients/**").hasAnyRole("MEDECIN", "AGENT_ADMISSION", "COMPTABLE", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/admission/chambres/**").hasAnyRole("MEDECIN", "AGENT_ADMISSION", "COMPTABLE", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/calendar/**").hasAnyRole("MEDECIN", "AGENT_ADMISSION", "COMPTABLE", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/dashboard/**").hasAnyRole("MEDECIN", "AGENT_ADMISSION", "COMPTABLE", "ADMIN")

                        // ========== CRÉATION (ADMIN + AGENT_ADMISSION) ==========
                        .requestMatchers(HttpMethod.POST, "/api/admission/patients/**").hasAnyRole("AGENT_ADMISSION", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/calendar/**").hasAnyRole("AGENT_ADMISSION", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/medical/consultations").hasAnyRole("AGENT_ADMISSION", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/medical/examens").hasAnyRole("MEDECIN", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/medical/prescriptions").hasAnyRole("MEDECIN", "ADMIN")

                        // ========== MODIFICATION (ADMIN + AGENT_ADMISSION) ==========
                        .requestMatchers(HttpMethod.PUT, "/api/admission/patients/**").hasAnyRole("AGENT_ADMISSION", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/admission/chambres/**").hasAnyRole("AGENT_ADMISSION", "ADMIN")

                        // ========== SUPPRESSION (ADMIN + AGENT_ADMISSION) ==========
                        .requestMatchers(HttpMethod.DELETE, "/api/admission/patients/**").hasAnyRole("AGENT_ADMISSION", "ADMIN")

                        // ========== FACTURATION (ADMIN + COMPTABLE) ==========
                        .requestMatchers("/api/facturation/**").hasAnyRole("COMPTABLE", "ADMIN")

                        // ========== STAFF (ADMIN uniquement) ==========
                        .requestMatchers("/api/staff/**").hasRole("ADMIN")

                        // ========== TOUT LE RESTE (authentifié) ==========
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:5174"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}