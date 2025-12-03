package com.example.away.controller;

import com.example.away.model.Usuario;
import com.example.away.security.service.JwtService;
import com.example.away.service.UsuarioService;
import com.example.away.dto.LoginRequest;
import com.example.away.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthenticationManager authenticationManager; // Para validar login e senha

    @Autowired
    private JwtService jwtService; // Para gerar o JWT

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Tenta autenticar o usuário através do Spring Security.
            // O AuthenticationManager chamará o seu UserDetailsService e PasswordEncoder.
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(), // O Spring Security usará este como 'username'
                            loginRequest.getSenha()
                    )
            );

            // Se a autenticação foi bem-sucedida, obtém os detalhes do usuário.
            // O metodo getPrincipal() retorna o objeto UserDetails.
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Busca o objeto Usuario para retornar os dados adicionais no corpo da resposta
            Usuario usuario = usuarioService.findByEmail(loginRequest.getEmail());

            // Gera o Token JWT usando o TokenService
            String jwtToken = jwtService.generateToken(userDetails);

            // Cria a resposta
            LoginResponse response = new LoginResponse();
            response.setToken(jwtToken);
            response.setUsuario(usuario); // Retorna os dados do usuário
            response.setExpiresIn(3600);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Captura exceções como BadCredentialsException (senha errada) ou
            // UsernameNotFoundException (usuário não encontrado).
            if (e instanceof org.springframework.security.core.AuthenticationException) {
                logger.warn("Falha na autenticação: Credenciais inválidas para email: {}", loginRequest.getEmail());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            logger.error("Erro interno no login: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Por enquanto só retorna sucesso
        // Implementar invalidação de token depois
        return ResponseEntity.ok().build();
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Backend AWAY está funcionando!");
    }
}
