package com.example.away.controller;

import com.example.away.exception.ResourceNotFoundException;
import com.example.away.exception.BusinessException;
import com.example.away.exception.ValidationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * Endpoint para testar ResourceNotFoundException
     */
    @GetMapping("/resource-not-found")
    public ResponseEntity<String> testResourceNotFound() {
        throw new ResourceNotFoundException("Recurso de teste", "id", 999L);
    }

    /**
     * Endpoint para testar BusinessException
     */
    @GetMapping("/business-error")
    public ResponseEntity<String> testBusinessError() {
        throw new BusinessException("Erro de negócio de teste", "TEST_ERROR");
    }

    /**
     * Endpoint para testar ValidationException
     */
    @GetMapping("/validation-error")
    public ResponseEntity<String> testValidationError() {
        List<String> errors = Arrays.asList(
            "Nome é obrigatório",
            "Email deve ter formato válido",
            "Idade deve ser maior que 0"
        );
        throw new ValidationException("Dados de entrada inválidos", errors);
    }

    /**
     * Endpoint para testar IllegalArgumentException
     */
    @GetMapping("/illegal-argument")
    public ResponseEntity<String> testIllegalArgument() {
        throw new IllegalArgumentException("Argumento inválido fornecido");
    }

    /**
     * Endpoint para testar exceção genérica
     */
    @GetMapping("/generic-error")
    public ResponseEntity<String> testGenericError() {
        throw new RuntimeException("Erro genérico de teste");
    }

    /**
     * Endpoint para testar validação de parâmetro
     */
    @GetMapping("/type-mismatch/{id}")
    public ResponseEntity<String> testTypeMismatch(@PathVariable String id) {
        // Este endpoint espera um Long, mas recebe String
        return ResponseEntity.ok("ID recebido: " + id);
    }

    /**
     * Endpoint para testar JSON malformado
     */
    @PostMapping("/invalid-json")
    public ResponseEntity<String> testInvalidJson(@RequestBody String json) {
        return ResponseEntity.ok("JSON recebido: " + json);
    }
}
