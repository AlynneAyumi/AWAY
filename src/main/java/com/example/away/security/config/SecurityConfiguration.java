package com.example.away.security.config;

import com.example.away.security.filter.JwtAuthenticationFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    @Lazy
    private JwtAuthenticationFilter jwtAuthFilter;

    @Autowired
    @Lazy
    private UserDetailsService userDetailsService;

    // Define as regras de acesso e o filtro JWT
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Desabilita CSRF, padrão para APIs REST stateless
                .authorizeHttpRequests(auth -> auth // Define as regras de autorização de requisições
                        .requestMatchers("/auth/**").permitAll() // Permite acesso irrestrito ao endpoint de autenticação (login/cadastro)
                        .requestMatchers(HttpMethod.POST, "/usuario/save").permitAll()
                        .anyRequest().authenticated() // Qualquer outra requisição deve ser autenticada
                )
                // Define a política de gerenciamento de sessão como STATELESS (sem sessão)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()) // Define o provedor de autenticação customizado
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // Adiciona o filtro JWT ANTES do filtro padrão de autenticação

        return http.build();
    }


    // Define o provedor de autenticação (como buscar o usuário e como validar a senha).
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService); // Usa o campo injetado
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // Bean para o BCrypt, essencial para hashing de senhas.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // O AuthenticationManager é usado no Controller de Login para realizar a autenticação.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
