package com.example.away.controller;

import com.example.away.model.Usuario;
import com.example.away.service.UsuarioService;
import com.example.away.dto.LoginRequest;
import com.example.away.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Buscar usuário por email
            Usuario usuario = usuarioService.findByEmail(loginRequest.getEmail());
            
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Verificar senha (implementação simples - melhorar com hash depois)
            if (!usuario.getSenha().equals(loginRequest.getSenha())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Gerar token simples (melhorar com JWT depois)
            String token = UUID.randomUUID().toString();

            // Criar resposta
            LoginResponse response = new LoginResponse();
            response.setToken(token);
            response.setUsuario(usuario);
            response.setExpiresIn(3600); // 1 hora

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("Erro no login: " + e.getMessage());
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
