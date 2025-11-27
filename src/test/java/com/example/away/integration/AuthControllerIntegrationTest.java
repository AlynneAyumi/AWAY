package com.example.away.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.away.controller.AuthController;
import com.example.away.dto.LoginRequest;
import com.example.away.dto.LoginResponse;
import com.example.away.model.Usuario;
import com.example.away.service.UsuarioService;

@ExtendWith(MockitoExtension.class)
@DisplayName("TESTE DE INTEGRAÇÃO - AuthController")
class AuthControllerIntegrationTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private AuthController authController;

    private Usuario usuario;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // --- Criando usuário ---
        usuario = new Usuario();
        usuario.setIdUsuario(1L);
        usuario.setNome("Teste Usuario");
        usuario.setEmail("teste@usuario.com");
        usuario.setSenha("123456");

        // --- LoginRequest válido ---
        loginRequest = new LoginRequest();
        loginRequest.setEmail("teste@usuario.com");
        loginRequest.setSenha("123456");
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Login com sucesso")
    void deveRealizarLoginComSucesso() {
        // --- Mock ---
        when(usuarioService.findByEmail("teste@usuario.com")).thenReturn(usuario);

        // --- Execução ---
        ResponseEntity<LoginResponse> response = authController.login(loginRequest);

        // --- Verificações ---
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(usuario.getEmail(), response.getBody().getUsuario().getEmail());
        assertNotNull(response.getBody().getToken(), "Token não deve ser nulo");
        assertEquals(3600, response.getBody().getExpiresIn(), "Expiração deve ser 3600 segundos");
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Login falha quando usuário não existe")
    void deveRetornarUnauthorizedQuandoUsuarioNaoExistir() {
        // --- Mock ---
        when(usuarioService.findByEmail("teste@usuario.com")).thenReturn(null);

        // --- Execução ---
        ResponseEntity<LoginResponse> response = authController.login(loginRequest);

        // --- Verificações ---
        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody(), "Body deve ser nulo quando usuário não existe");
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Login falha quando senha estiver incorreta")
    void deveRetornarUnauthorizedQuandoSenhaIncorreta() {
        // --- Mock ---
        when(usuarioService.findByEmail("teste@usuario.com")).thenReturn(usuario);

        // --- Alterar senha incorreta ---
        loginRequest.setSenha("senhaErrada");

        // --- Execução ---
        ResponseEntity<LoginResponse> response = authController.login(loginRequest);

        // --- Verificações ---
        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody(), "Body deve ser nulo quando senha está incorreta");
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Logout deve retornar sucesso")
    void deveRetornarOkNoLogout() {
        // --- Execução ---
        ResponseEntity<?> response = authController.logout();

        // --- Verificações ---
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Health check deve retornar mensagem de status")
    void deveRetornarMensagemNoHealth() {
        // --- Execução ---
        ResponseEntity<String> response = authController.health();

        // --- Verificações ---
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Backend AWAY está funcionando!", response.getBody());
    }
}
