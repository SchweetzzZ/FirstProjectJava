package com.firstContact.projetoUm.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Desativa proteção CSRF porque nossa API usa tokens, não cookies
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // API REST sem estado (Stateless)
                .authorizeHttpRequests(authorize -> authorize
                                .anyRequest().permitAll()
//                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll() // Rota de login pública
//                        .requestMatchers(HttpMethod.POST, "/users").permitAll()      // Rota de cadastro pública
//                        .requestMatchers(HttpMethod.DELETE, "/users/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/users/**").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/users").permitAll()
//                        .anyRequest().authenticated()                                // Qualquer outra rota exige token JWT!
                )
                // Adiciona o nosso filtro customizado ANTES do filtro padrão de Username/Password do Spring
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // para o spring gerenciar a autenticação internamente
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration){
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

