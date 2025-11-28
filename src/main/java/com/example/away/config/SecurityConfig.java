package com.example.away.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    //@Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    // Usado para testar se as criptografias funcionaram
    /*
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Desabilita CSRF (Comum em APIs REST sem sessão)
            .csrf(AbstractHttpConfigurer::disable)
            
            // 2. Configuração das Regras de Autorização
            .authorizeHttpRequests(auth -> auth
                // PERMITIR ACESSO PÚBLICO (Whitelist)
                // Substitua "/usuarios" pelo seu endpoint real de POST para cadastro.
                // Ex: Se o seu Controller tem um @PostMapping("/auth/register"), use "/auth/register"
                .requestMatchers("/usuario/save", "/usuario/findById/*").permitAll()
                
                // Rotas estáticas (como swagger, se for usar)
                //.requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                
                // QUALQUER OUTRA ROTA: DEVE ESTAR AUTENTICADA
                .anyRequest().authenticated()
            )
            
            // 3. Configura a gestão de sessão como stateless (essencial para JWT)
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 4. Adicionar o Filtro JWT aqui (quando você implementá-lo no próximo passo)
        // http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    */
}