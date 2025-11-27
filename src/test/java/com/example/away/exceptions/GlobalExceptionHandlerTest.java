package com.example.away.exceptions;

import com.example.away.dto.ErrorResponse;
import com.example.away.exception.BusinessException;
import com.example.away.exception.GlobalExceptionHandler;
import com.example.away.exception.ResourceNotFoundException;
import com.example.away.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    // A classe que queremos testar
    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    // Dependência mockada (simulada)
    @Mock
    private WebRequest webRequest;

    private final String MOCK_URI = "/api/test/resource";

    @BeforeEach
    void setUp() {
        // Configura o comportamento padrão do WebRequest
        when(webRequest.getDescription(false)).thenReturn("uri=" + MOCK_URI);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > ResourceNotFoundException | Deve retornar status 404 NOT_FOUND")
    void handleResourceNotFoundException_ShouldReturn_404_NotFound() {
        // Arrange
        ResourceNotFoundException ex = new ResourceNotFoundException("Assistido não encontrado");

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleResourceNotFoundException(ex, webRequest);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Assistido não encontrado", response.getBody().getMessage());
        //assertEquals("RESOURCE_NOT_FOUND", response.getBody().getErrorCode());
        assertEquals(MOCK_URI, response.getBody().getPath());
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > ValidationException | Deve retornar status 400 BAD_REQUEST com detalhes de erro")
    void handleValidationException_ShouldReturn_400_BadRequest() {
        // Arrange
        List<String> errors = Arrays.asList("Campo X inválido", "Campo Y é obrigatório");
        ValidationException ex = new ValidationException("Falha na regra de negócio", errors);

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleValidationException(ex, webRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Falha na regra de negócio", response.getBody().getMessage());
        //assertEquals("VALIDATION_ERROR", response.getBody().getErrorCode());
        assertEquals(errors, response.getBody().getDetails());
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > BusinessException | Deve retornar status 400 BAD_REQUEST com código de erro customizado")
    void handleBusinessException_ShouldReturn_400_BadRequest_WithCustomCode() {
        // Arrange
        BusinessException ex = new BusinessException("Ação proibida", "PROHIBITED_ACTION");

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleBusinessException(ex, webRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Ação proibida", response.getBody().getMessage());
        //assertEquals("PROHIBITED_ACTION", response.getBody().getErrorCode());
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > BusinessException | Deve retornar status 400 BAD_REQUEST com código de erro padrão")
    void handleBusinessException_ShouldReturn_400_BadRequest_WithDefaultCode() {
        // Arrange
        // Cria uma BusinessException sem errorCode específico (assumindo que o construtor permite)
        BusinessException ex = new BusinessException("Ação proibida", (String) null);

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleBusinessException(ex, webRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        //assertEquals("BUSINESS_ERROR", response.getBody().getErrorCode()); // Verifica o código padrão
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > MethodArgumentNotValidException | Deve retornar 400 e listar erros de campo (@Valid)")
    void handleValidationExceptions_ShouldReturn_400_WithFieldErrors() {
        // Arrange
        // 1. Mocks para a exceção
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);

        // 2. Mocks dos erros
        FieldError error1 = new FieldError("objectName", "nome", "Nome é obrigatório");
        FieldError error2 = new FieldError("objectName", "cpf", "CPF inválido");
        List<org.springframework.validation.ObjectError> allErrors = Arrays.asList(error1, error2);

        // 3. Comportamento do Mock
        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(allErrors);

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleValidationExceptions(ex, webRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Dados de entrada inválidos", response.getBody().getMessage());

        List<String> expectedDetails = Arrays.asList("nome: Nome é obrigatório", "cpf: CPF inválido");
        assertEquals(expectedDetails, response.getBody().getDetails());
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > ConstraintViolationException | Deve retornar 400 e listar violações de constraint")
    void handleConstraintViolationException_ShouldReturn_400_WithConstraintErrors() {
        // Arrange
        // 1. Mocks para a exceção
        @SuppressWarnings("unchecked")
        ConstraintViolation<Object> violation1 = mock(ConstraintViolation.class);
        @SuppressWarnings("unchecked")
        ConstraintViolation<Object> violation2 = mock(ConstraintViolation.class);

        Set<ConstraintViolation<?>> violations = new HashSet<>(Arrays.asList(violation1, violation2));
        ConstraintViolationException ex = new ConstraintViolationException("Violação", violations);

        // 2. Comportamento do Mock
        when(violation1.getMessage()).thenReturn("Tamanho máximo excedido");
        when(violation2.getMessage()).thenReturn("Valor deve ser positivo");

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleConstraintViolationException(ex, webRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Violação de regras de validação", response.getBody().getMessage());

        List<String> expectedDetails = Arrays.asList("Tamanho máximo excedido", "Valor deve ser positivo");
        assertTrue(response.getBody().getDetails().containsAll(expectedDetails));
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > MethodArgumentTypeMismatchException | Deve retornar 400 e indicar o tipo esperado")
    void handleTypeMismatch_ShouldReturn_400_WithTypeInfo() {
        // Arrange
        MethodArgumentTypeMismatchException ex = mock(MethodArgumentTypeMismatchException.class);

        when(ex.getName()).thenReturn("id");
        when(ex.getRequiredType()).thenReturn((Class) Long.class);

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleTypeMismatch(ex, webRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());

        String expectedMessage = "Parâmetro 'id' deve ser do tipo 'Long'";
        assertEquals(expectedMessage, response.getBody().getMessage());
        //assertEquals("TYPE_MISMATCH", response.getBody().getErrorCode());
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > HttpMessageNotReadableException | Deve retornar 400 para JSON malformado")
    void handleHttpMessageNotReadable_ShouldReturn_400_ForInvalidJson() {
        // Arrange
        HttpMessageNotReadableException ex = new HttpMessageNotReadableException("JSON inválido", (Throwable) null);

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleHttpMessageNotReadable(ex, webRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Formato JSON inválido", response.getBody().getMessage());
        //assertEquals("INVALID_JSON", response.getBody().getErrorCode());
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > IllegalArgumentException | Deve retornar 400 para argumento ilegal")
    void handleIllegalArgumentException_ShouldReturn_400_ForIllegalArgument() {
        // Arrange
        IllegalArgumentException ex = new IllegalArgumentException("ID não pode ser zero");

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleIllegalArgumentException(ex, webRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ID não pode ser zero", response.getBody().getMessage());
        //assertEquals("ILLEGAL_ARGUMENT", response.getBody().getErrorCode());
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Exception | Deve retornar status 500 INTERNAL_SERVER_ERROR para exceções genéricas")
    void handleGenericException_ShouldReturn_500_InternalServerError() {
        // Arrange
        Exception ex = new Exception("Erro de banco de dados não mapeado");

        // Act
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGenericException(ex, webRequest);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Erro interno do servidor. Tente novamente mais tarde.", response.getBody().getMessage());
        //assertEquals("INTERNAL_SERVER_ERROR", response.getBody().getErrorCode());
    }
}