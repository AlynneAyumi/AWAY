package com.example.away.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

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

import com.example.away.controller.EnderecoController;
import com.example.away.model.Endereco;
import com.example.away.service.EnderecoService;

@ExtendWith(MockitoExtension.class)
@DisplayName("TESTE DE INTEGRAÇÃO - EnderecoController")
class EnderecoIntegrationTest {

    @Mock
    private EnderecoService enderecoService;

    @InjectMocks
    private EnderecoController enderecoController;

    private Endereco endereco;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        endereco = new Endereco();
        endereco.setIdEndereco(1L);
        endereco.setCep("85800-000");
        endereco.setBairro("Centro");
        endereco.setCidade("Foz do Iguaçu");
        endereco.setEstado("PR");
        endereco.setLogradouro("Rua das Palmeiras");
        endereco.setNumero("123");
        endereco.setComplemento("Casa 2");
    }

    // ==========================================================
    // CREATE
    // ==========================================================
    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve salvar endereço com sucesso")
    void deveSalvarEnderecoComSucesso() {
        when(enderecoService.save(any(Endereco.class))).thenReturn(endereco);

        ResponseEntity<Endereco> response = enderecoController.save(endereco);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Foz do Iguaçu", response.getBody().getCidade());
        assertEquals("Rua das Palmeiras", response.getBody().getLogradouro());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve retornar BadRequest ao tentar salvar endereço inválido")
    void deveRetornarBadRequestAoSalvarEnderecoInvalido() {
        when(enderecoService.save(any(Endereco.class))).thenThrow(RuntimeException.class);

        ResponseEntity<Endereco> response = enderecoController.save(new Endereco());

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // ==========================================================
    // READ - FIND ALL
    // ==========================================================
    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve retornar lista de endereços no findAll")
    void deveRetornarListaDeEnderecosNoFindAll() {
        when(enderecoService.findAll()).thenReturn(List.of(endereco));

        ResponseEntity<List<Endereco>> response = enderecoController.findAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Foz do Iguaçu", response.getBody().get(0).getCidade());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve retornar BadRequest quando findAll lançar exceção")
    void deveRetornarBadRequestQuandoFindAllLancarExcecao() {
        when(enderecoService.findAll()).thenThrow(RuntimeException.class);

        ResponseEntity<List<Endereco>> response = enderecoController.findAll();

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // ==========================================================
    // READ - FIND BY ID
    // ==========================================================
    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve retornar endereço existente no findById")
    void deveRetornarEnderecoExistenteNoFindById() {
        when(enderecoService.findById(1L)).thenReturn(endereco);

        ResponseEntity<Endereco> response = enderecoController.findById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Foz do Iguaçu", response.getBody().getCidade());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve retornar BadRequest quando findById lançar exceção")
    void deveRetornarBadRequestQuandoFindByIdLancarExcecao() {
        when(enderecoService.findById(1L)).thenThrow(RuntimeException.class);

        ResponseEntity<Endereco> response = enderecoController.findById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // ==========================================================
    // UPDATE
    // ==========================================================
    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve atualizar endereço com sucesso")
    void deveAtualizarEnderecoComSucesso() {
        when(enderecoService.update(any(Long.class), any(Endereco.class))).thenReturn(endereco);

        ResponseEntity<Endereco> response = enderecoController.update(1L, endereco);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Rua das Palmeiras", response.getBody().getLogradouro());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve retornar BadRequest ao atualizar endereço inexistente")
    void deveRetornarBadRequestAoAtualizarEnderecoInexistente() {
        when(enderecoService.update(any(Long.class), any(Endereco.class))).thenThrow(RuntimeException.class);

        ResponseEntity<Endereco> response = enderecoController.update(999L, endereco);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // ==========================================================
    // DELETE
    // ==========================================================
    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve deletar endereço com sucesso")
    void deveDeletarEnderecoComSucesso() {
        doNothing().when(enderecoService).delete(1L);

        ResponseEntity<?> response = enderecoController.delete(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve retornar BadRequest ao tentar deletar endereço inexistente")
    void deveRetornarBadRequestAoDeletarEnderecoInexistente() {
        // Simula erro na exclusão
        doNothing().when(enderecoService).delete(1L);
        when(enderecoService.findById(999L)).thenThrow(RuntimeException.class);

        ResponseEntity<?> response = enderecoController.delete(999L);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // ==========================================================
    // FIND BY CEP
    // ==========================================================
    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve buscar endereço por CEP com sucesso")
    void deveBuscarEnderecoPorCepComSucesso() {
        when(enderecoService.buscarPorCep("85800-000")).thenReturn(endereco);

        ResponseEntity<Endereco> response = enderecoController.buscarEnderecoPorCep("85800-000");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Foz do Iguaçu", response.getBody().getCidade());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve retornar NOT_FOUND ao buscar CEP inexistente")
    void deveRetornarNotFoundAoBuscarCepInexistente() {
        when(enderecoService.buscarPorCep("99999-999")).thenThrow(IllegalArgumentException.class);

        ResponseEntity<Endereco> response = enderecoController.buscarEnderecoPorCep("99999-999");

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // ==========================================================
    // FIND BY CIDADE
    // ==========================================================
    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve buscar endereço por cidade com sucesso")
    void deveBuscarEnderecoPorCidadeComSucesso() {
        when(enderecoService.buscarPorCidade("Foz do Iguaçu")).thenReturn(endereco);

        ResponseEntity<Endereco> response = enderecoController.buscarEnderecoPorCidade("Foz do Iguaçu");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("85800-000", response.getBody().getCep());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve retornar NOT_FOUND ao buscar cidade inexistente")
    void deveRetornarNotFoundAoBuscarCidadeInexistente() {
        when(enderecoService.buscarPorCidade("Curitiba")).thenThrow(IllegalArgumentException.class);

        ResponseEntity<Endereco> response = enderecoController.buscarEnderecoPorCidade("Curitiba");

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
