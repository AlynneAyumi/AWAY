package com.example.away.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.List;

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

import com.example.away.controller.UsuarioController;
import com.example.away.exception.BusinessException;
import com.example.away.exception.ResourceNotFoundException;
import com.example.away.model.Pessoa;
import com.example.away.model.Usuario;
import com.example.away.service.UsuarioService;

@ExtendWith(MockitoExtension.class)
@DisplayName("TESTE DE INTEGRAÇÃO - UsuarioController")
class UsuarioIntegrationTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private Usuario usuario;
    private Pessoa pessoa;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pessoa = new Pessoa();
        pessoa.setNome("Carlos");
        pessoa.setSegundoNome("Silva");
        pessoa.setCpf("12345678900");
        pessoa.setTelefone("44999998888");

        usuario = new Usuario();
        usuario.setIdUsuario(1L);
        usuario.setNomeUser("carlos.silva");
        usuario.setEmail("carlos@email.com");
        usuario.setSenha("123456");
        usuario.setPerfil("AGENTE");
        usuario.setAtivo(true);
        usuario.setPessoa(pessoa);
        usuario.setCreationDate(new Date());
        usuario.setLastUpdateDate(new Date());
    }

    // =====================================================
    // SAVE (POST)
    // =====================================================
    @Test
    @DisplayName("Deve criar usuário com sucesso")
    void deveCriarUsuarioComSucesso() {
        when(usuarioService.save(any(Usuario.class))).thenReturn(usuario);

        ResponseEntity<Usuario> response = usuarioController.save(usuario);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("carlos.silva", response.getBody().getNomeUser());
        assertEquals("carlos@email.com", response.getBody().getEmail());
    }

    @Test
    @DisplayName("Deve lançar exceção BusinessException quando usuário for nulo no save()")
    void deveLancarExcecaoQuandoUsuarioForNuloNoSave() {
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> usuarioController.save(null)
        );

        assertEquals("Dados do usuário são obrigatórios", exception.getMessage());
    }

    // =====================================================
    // FIND ALL (GET)
    // =====================================================
    @Test
    @DisplayName("Deve retornar lista de usuários no findAll()")
    void deveRetornarListaDeUsuarios() {
        when(usuarioService.findAll()).thenReturn(List.of(usuario));

        ResponseEntity<List<Usuario>> response = usuarioController.findAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("carlos.silva", response.getBody().get(0).getNomeUser());
    }

    // =====================================================
    // FIND BY ID (GET)
    // =====================================================
    @Test
    @DisplayName("Deve retornar usuário existente no findById()")
    void deveRetornarUsuarioExistente() {
        when(usuarioService.findById(1L)).thenReturn(usuario);

        ResponseEntity<Usuario> response = usuarioController.findById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("carlos.silva", response.getBody().getNomeUser());
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando usuário não existir no findById()")
    void deveLancarExcecaoQuandoUsuarioNaoExistir() {
        when(usuarioService.findById(999L)).thenReturn(null);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> usuarioController.findById(999L)
        );

        assertTrue(exception.getMessage().contains("Usuário"));
        assertTrue(exception.getMessage().contains("999"));
    }

    // =====================================================
    // UPDATE (PUT)
    // =====================================================
    @Test
    @DisplayName("Deve atualizar usuário existente com sucesso")
    void deveAtualizarUsuarioComSucesso() {
        when(usuarioService.update(any(Long.class), any(Usuario.class))).thenReturn(usuario);

        ResponseEntity<Usuario> response = usuarioController.update(1L, usuario);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("carlos.silva", response.getBody().getNomeUser());
    }

    @Test
    @DisplayName("Deve lançar BusinessException quando objeto de update for nulo")
    void deveLancarExcecaoQuandoUpdateForNulo() {
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> usuarioController.update(1L, null)
        );

        assertEquals("Dados do usuário são obrigatórios", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao atualizar usuário inexistente")
    void deveLancarExcecaoAoAtualizarUsuarioInexistente() {
        when(usuarioService.update(any(Long.class), any(Usuario.class))).thenReturn(null);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> usuarioController.update(999L, usuario)
        );

        assertTrue(exception.getMessage().contains("Usuário"));
    }

    // =====================================================
    // DELETE (DELETE)
    // =====================================================
    @Test
    @DisplayName("Deve deletar usuário existente com sucesso")
    void deveDeletarUsuarioComSucesso() {
        when(usuarioService.findById(1L)).thenReturn(usuario);
        doNothing().when(usuarioService).delete(1L);

        ResponseEntity<?> response = usuarioController.delete(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(usuarioService, times(1)).delete(1L);
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao deletar usuário inexistente")
    void deveLancarExcecaoAoDeletarUsuarioInexistente() {
        when(usuarioService.findById(999L)).thenReturn(null);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> usuarioController.delete(999L)
        );

        assertTrue(exception.getMessage().contains("Usuário"));
        assertTrue(exception.getMessage().contains("999"));
    }

    // =====================================================
    // TEST (ENDPOINT DE TESTE)
    // =====================================================
    @Test
    @DisplayName("Deve retornar 'Teste OK' no endpoint /test")
    void deveRetornarTesteOk() {
        ResponseEntity<String> response = usuarioController.test();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Teste OK", response.getBody());
    }
}
