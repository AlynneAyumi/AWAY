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

import com.example.away.controller.AssistidoController;
import com.example.away.dto.AssistidoCreateRequest;
import com.example.away.dto.AssistidoUpdateRequest;
import com.example.away.exception.BusinessException;
import com.example.away.exception.ResourceNotFoundException;
import com.example.away.model.Assistido;
import com.example.away.model.Endereco;
import com.example.away.model.Pessoa;
import com.example.away.repository.AssistidoRepository;
import com.example.away.service.AssistidoService;
import com.example.away.service.EnderecoService;
import com.example.away.service.PessoaService;

@ExtendWith(MockitoExtension.class)
@DisplayName("TESTE DE INTEGRAÇÃO - AssistidoController")
class AssistidoIntegrationTest {

    @Mock
    private AssistidoRepository assistidoRepository;

    @Mock
    private EnderecoService enderecoService;

    @Mock
    private AssistidoService assistidoService;

    @Mock
    private PessoaService pessoaService;

    @InjectMocks
    private AssistidoController assistidoController;

    private Assistido assistido;
    private Pessoa pessoa;
    private AssistidoCreateRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // --- Criando objetos ---
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua das Flores");

        pessoa = new Pessoa();
        pessoa.setNome("João");
        pessoa.setSegundoNome("Maria");
        pessoa.setCpf("12345678909");
        pessoa.setTelefone("45912345678");
        pessoa.setEndereco(endereco);

        assistido = new Assistido();
        assistido.setPessoa(pessoa);

        // --- DTO (request) ---
        AssistidoCreateRequest.EnderecoData enderecoData = new AssistidoCreateRequest.EnderecoData();
        enderecoData.setLogradouro("Rua das Flores");

        AssistidoCreateRequest.PessoaData pessoaData = new AssistidoCreateRequest.PessoaData();
        pessoaData.setNome("João");
        pessoaData.setSegundoNome("Maria");
        pessoaData.setCpf("12345678909");
        pessoaData.setTelefone("45912345678");
        pessoaData.setEndereco(enderecoData);

        request = new AssistidoCreateRequest();
        request.setPessoa(pessoaData);
        request.setNumAuto("123");
        request.setNumProcesso("456");
        request.setObservacao("Teste observação");
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de sucesso ao criar Assistido")
    void deveCriarAssistidoComSucesso() {
        // --- Mocks ---
        when(pessoaService.save(any(Pessoa.class))).thenReturn(pessoa);
        when(assistidoService.save(any(Assistido.class))).thenReturn(assistido);

        // --- Execução ---
        ResponseEntity<Map<String, Object>> response = assistidoController.save(request);

        // --- Verificações ---
        assertNotNull(response, "Response não deve ser nulo");
        assertNotNull(response.getBody(), "Body não deve ser nulo");

        Assistido resultado = (Assistido) response.getBody().get("assistido");
        assertNotNull(resultado, "Assistido não deve ser nulo");
        assertNotNull(resultado.getPessoa(), "Pessoa não deve ser nula");
        assertEquals("João", resultado.getPessoa().getNome(), "Nome deve ser João");
        assertEquals("Rua das Flores", resultado.getPessoa().getEndereco().getLogradouro(), "Logradouro deve ser Rua das Flores");
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve lançar exceção quando request for nulo")
    void deveLancarExcecaoQuandoRequestForNulo() {
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> assistidoController.save(null)
        );
        assertEquals("Dados do assistido são obrigatórios", exception.getMessage());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve lançar exceção quando pessoa for nula")
    void deveLancarExcecaoQuandoPessoaForNula() {
        AssistidoCreateRequest req = new AssistidoCreateRequest();
        req.setPessoa(null);

        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> assistidoController.save(req)
        );
        assertEquals("Dados do assistido são obrigatórios", exception.getMessage());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de sucesso ao deletar Assistido")
    void deveDeletarAssistidoComSucesso() {
        Long assistidoId = 1L;

        // --- Mocks ---
        when(assistidoService.findById(assistidoId)).thenReturn(assistido);

        // Simula o comportamento do método delete sem lançar exceção
        doNothing().when(assistidoService).delete(assistidoId);

        // --- Execução ---
        ResponseEntity<Map<String, Object>> response = assistidoController.delete(assistidoId);

        // --- Verificações ---
        assertNotNull(response, "Response não deve ser nulo");
        assertNotNull(response.getBody(), "Body não deve ser nulo");
        assertEquals("Assistido excluído com sucesso!", response.getBody().get("message"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve retornar NOT_FOUND ao tentar deletar Assistido inexistente")
    void deveRetornarNotFoundAoTentarDeletarAssistidoInexistente() {
        Long assistidoId = 999L;

        // --- Mocka o retorno nulo ---
        when(assistidoService.findById(assistidoId)).thenReturn(null);

        // --- Executa ---
        ResponseEntity<Map<String, Object>> response = assistidoController.delete(assistidoId);

        // --- Verificações ---
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Assistido não encontrado", response.getBody().get("message"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - FindAll deve retornar lista de assistidos")
    void deveRetornarListaDeAssistidosNoFindAll() {
        // --- Mocks ---
        when(assistidoService.findAll()).thenReturn(List.of(assistido));

        // --- Execução ---
        ResponseEntity<List<Assistido>> response = assistidoController.findAll();

        // --- Verificações ---
        assertNotNull(response, "Response não deve ser nulo");
        assertNotNull(response.getBody(), "Body não deve ser nulo");
        assertEquals(1, response.getBody().size(), "Deve conter 1 assistido na lista");
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - FindById deve retornar assistido existente")
    void deveRetornarAssistidoExistenteNoFindById() {
        Long assistidoId = 1L;

        // --- Mocks ---
        when(assistidoService.findById(assistidoId)).thenReturn(assistido);

        // --- Execução ---
        ResponseEntity<Assistido> response = assistidoController.findById(assistidoId);

        // --- Verificações ---
        assertNotNull(response, "Response não deve ser nulo");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Status deve ser 200 OK");
        assertNotNull(response.getBody(), "Body não deve ser nulo");
        assertEquals("João", response.getBody().getPessoa().getNome(), "Nome deve ser João");
        assertEquals("Rua das Flores", response.getBody().getPessoa().getEndereco().getLogradouro(), "Logradouro deve ser Rua das Flores");
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - FindById deve lançar ResourceNotFoundException quando assistido não existir")
    void deveLancarExcecaoQuandoAssistidoNaoExistirNoFindById() {
        Long assistidoId = 999L;

        // --- Mocka o retorno nulo ---
        when(assistidoService.findById(assistidoId)).thenReturn(null);

        // --- Execução & Verificação ---
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> assistidoController.findById(assistidoId)
        );

        // --- Asserts ---
        assertTrue(exception.getMessage().contains("Assistido"));
        assertTrue(exception.getMessage().contains("999"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve atualizar assistido existente com sucesso")
    void deveAtualizarAssistidoExistenteComSucesso() throws Exception {
        Long assistidoId = 1L;

        // --- Mock do assistido existente ---
        when(assistidoService.findById(assistidoId)).thenReturn(assistido);

        // --- Mock do update ---
        when(assistidoService.update(any(Long.class), any(Assistido.class))).thenReturn(assistido);

        // --- Criar request ---
        AssistidoUpdateRequest.PessoaUpdateDTO pessoaData = new AssistidoUpdateRequest.PessoaUpdateDTO();
        pessoaData.setNome("João Atualizado");
        pessoaData.setSegundoNome("Maria Atualizada");
        pessoaData.setCpf("12345678909");
        pessoaData.setTelefone("45912345678");

        AssistidoUpdateRequest request = new AssistidoUpdateRequest();
        request.setNumAuto("999");
        request.setNumProcesso("888");
        request.setObservacao("Observação atualizada");
        request.setPessoa(pessoaData);

        // --- Execução ---
        ResponseEntity<Map<String, Object>> response = assistidoController.update(assistidoId, request);

        // --- Verificações ---
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(true, response.getBody().get("success"));
        assertEquals("Assistido atualizado com sucesso!", response.getBody().get("message"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve retornar NOT_FOUND ao atualizar assistido inexistente")
    void deveRetornarNotFoundAoAtualizarAssistidoInexistente() {
        Long assistidoId = 999L;

        // --- Mock do retorno nulo ---
        when(assistidoService.findById(assistidoId)).thenReturn(null);

        // --- Criar request simples ---
        AssistidoUpdateRequest request = new AssistidoUpdateRequest();
        request.setNumAuto("123");
        request.setNumProcesso("456");

        // --- Execução ---
        ResponseEntity<Map<String, Object>> response = assistidoController.update(assistidoId, request);

        // --- Verificações ---
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(false, response.getBody().get("success"));
        assertEquals("Assistido não encontrado", response.getBody().get("message"));
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Buscar assistido por numAuto")
    void deveBuscarAssistidoPorNumAuto() {
        String numAuto = "123";

        // --- Mock ---
        when(assistidoService.buscarPorNumAuto(numAuto)).thenReturn(List.of(assistido));

        // --- Execução ---
        ResponseEntity<List<Assistido>> response = assistidoController.buscarPorNumAuto(numAuto);

        // --- Verificações ---
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("João", response.getBody().get(0).getPessoa().getNome());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Buscar assistido por numProcesso")
    void deveBuscarAssistidoPorNumProcesso() {
        String numProcesso = "456";

        // --- Mock ---
        when(assistidoService.buscarPorNumProcesso(numProcesso)).thenReturn(List.of(assistido));

        // --- Execução ---
        ResponseEntity<List<Assistido>> response = assistidoController.buscarPorNumProcesso(numProcesso);

        // --- Verificações ---
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("João", response.getBody().get(0).getPessoa().getNome());
    }

    @Test
    @DisplayName("TESTE DE INTEGRÃO - Buscar assistido por numAuto inexistente deve retornar lista vazia")
    void deveRetornarListaVaziaQuandoNumAutoNaoExistir() {
        String numAutoInexistente = "999";

        // --- Mock ---
        when(assistidoService.buscarPorNumAuto(numAutoInexistente)).thenReturn(List.of());

        // --- Execução ---
        ResponseEntity<List<Assistido>> response = assistidoController.buscarPorNumAuto(numAutoInexistente);

        // --- Verificações ---
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty(), "A lista deve estar vazia quando nenhum assistido for encontrado");
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Buscar assistido por numProcesso inexistente deve retornar lista vazia")
    void deveRetornarListaVaziaQuandoNumProcessoNaoExistir() {
        String numProcessoInexistente = "888";

        // --- Mock ---
        when(assistidoService.buscarPorNumProcesso(numProcessoInexistente)).thenReturn(List.of());

        // --- Execução ---
        ResponseEntity<List<Assistido>> response = assistidoController.buscarPorNumProcesso(numProcessoInexistente);

        // --- Verificações ---
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty(), "A lista deve estar vazia quando nenhum assistido for encontrado");
    }


}
