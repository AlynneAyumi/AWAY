package com.example.away.controller;

import com.example.away.service.AuthService;
import com.example.away.dto.LoginRequest;
import com.example.away.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(this.service.login(loginRequest));
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
