package com.example.away.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

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

import com.example.away.controller.PessoaController;
import com.example.away.exception.ResourceNotFoundException;
import com.example.away.model.Endereco;
import com.example.away.model.Pessoa;
import com.example.away.service.PessoaService;

@ExtendWith(MockitoExtension.class)
@DisplayName("TESTE DE INTEGRAÇÃO - PessoaController")
class PessoaIntegrationTest {

    @Mock
    private PessoaService pessoaService;

    @InjectMocks
    private PessoaController pessoaController;

    private Pessoa pessoa;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Criando endereço
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua das Palmeiras");

        // Criando pessoa
        pessoa = new Pessoa();
        pessoa.setIdPessoa(1L);
        pessoa.setNome("Carlos");
        pessoa.setSegundoNome("Silva");
        pessoa.setCpf("98765432100");
        pessoa.setTelefone("45987654321");
        pessoa.setEndereco(endereco);
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - findAll deve retornar lista de pessoas")
    void deveRetornarListaDePessoasNoFindAll() {
        when(pessoaService.findAll()).thenReturn(List.of(pessoa));

        ResponseEntity<List<Pessoa>> response = pessoaController.findAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Carlos", response.getBody().get(0).getNome());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - save deve criar pessoa com sucesso")
    void deveCriarPessoaComSucesso() {
        when(pessoaService.save(any(Pessoa.class))).thenReturn(pessoa);

        ResponseEntity<Pessoa> response = pessoaController.save(pessoa);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Carlos", response.getBody().getNome());
        assertEquals("Rua das Palmeiras", response.getBody().getEndereco().getLogradouro());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - findById deve retornar pessoa existente")
    void deveRetornarPessoaExistenteNoFindById() {
        Long pessoaId = 1L;
        when(pessoaService.findById(pessoaId)).thenReturn(pessoa);

        ResponseEntity<Pessoa> response = pessoaController.findById(pessoaId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Carlos", response.getBody().getNome());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - findById deve lançar ResourceNotFoundException quando pessoa não existir")
    void deveLancarExcecaoQuandoPessoaNaoExistirNoFindById() {
        Long pessoaId = 999L;
        when(pessoaService.findById(pessoaId)).thenThrow(new ResourceNotFoundException("Pessoa não encontrada"));

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> pessoaController.findById(pessoaId)
        );

        assertTrue(exception.getMessage().contains("Pessoa"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - delete deve remover pessoa existente")
    void deveDeletarPessoaComSucesso() {
        Long pessoaId = 1L;
        doNothing().when(pessoaService).delete(pessoaId);

        ResponseEntity<?> response = pessoaController.delete(pessoaId);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - update deve atualizar pessoa existente")
    void deveAtualizarPessoaExistenteComSucesso() {
        Long pessoaId = 1L;
        Pessoa pessoaAtualizada = new Pessoa();
        pessoaAtualizada.setNome("Carlos Atualizado");

        when(pessoaService.update(pessoaId, pessoa)).thenReturn(pessoaAtualizada);

        ResponseEntity<Pessoa> response = pessoaController.update(pessoaId, pessoa);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Carlos Atualizado", response.getBody().getNome());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - buscarPorCpf deve retornar pessoa existente")
    void deveBuscarPessoaPorCpfExistente() {
        String cpf = "98765432100";
        when(pessoaService.buscarPessoaPorCpf(cpf)).thenReturn(pessoa);

        ResponseEntity<Pessoa> response = pessoaController.buscarPorCpf(cpf);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Carlos", response.getBody().getNome());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - buscarPorCpf deve retornar NOT_FOUND quando cpf não existir")
    void deveRetornarNotFoundQuandoCpfNaoExistir() {
        String cpf = "00000000000";
        when(pessoaService.buscarPessoaPorCpf(cpf)).thenThrow(new ResourceNotFoundException("Pessoa não encontrada"));

        ResponseEntity<Pessoa> response = pessoaController.buscarPorCpf(cpf);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
