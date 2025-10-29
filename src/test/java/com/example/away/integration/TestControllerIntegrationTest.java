package com.example.away.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.away.controller.TestController;
import com.example.away.exception.BusinessException;
import com.example.away.exception.ResourceNotFoundException;
import com.example.away.exception.ValidationException;

@ExtendWith(MockitoExtension.class)
@DisplayName("TESTE DE INTEGRAÇÃO - TestController")
class TestControllerIntegrationTest {

    @InjectMocks
    private TestController testController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve lançar ResourceNotFoundException")
    void deveLancarResourceNotFoundException() {
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> testController.testResourceNotFound()
        );

        assertTrue(exception.getMessage().contains("Recurso de teste"));
        assertEquals("id", exception.getFieldName());
        assertEquals(999L, exception.getFieldValue());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve lançar BusinessException")
    void deveLancarBusinessException() {
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> testController.testBusinessError()
        );

        assertEquals("Erro de negócio de teste", exception.getMessage());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve lançar ValidationException")
    void deveLancarValidationException() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> testController.testValidationError()
        );

        // Apenas verificando a mensagem principal
        assertEquals("Dados de entrada inválidos", exception.getMessage());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve lançar IllegalArgumentException")
    void deveLancarIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> testController.testIllegalArgument()
        );

        assertEquals("Argumento inválido fornecido", exception.getMessage());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve lançar RuntimeException genérica")
    void deveLancarRuntimeExceptionGenerica() {
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> testController.testGenericError()
        );

        assertEquals("Erro genérico de teste", exception.getMessage());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve aceitar String em type-mismatch")
    void deveAceitarStringEmTypeMismatch() {
        String id = "abc123";
        ResponseEntity<String> response = testController.testTypeMismatch(id);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("ID recebido: abc123", response.getBody());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve aceitar JSON malformado")
    void deveAceitarJsonMalformado() {
        String json = "{nome: 'teste'}"; // JSON simples
        ResponseEntity<String> response = testController.testInvalidJson(json);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("JSON recebido: {nome: 'teste'}", response.getBody());
    }
}
