package com.example.away.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.util.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;

import com.example.away.controller.AssistidoController;
import com.example.away.dto.AssistidoCreateRequest;
import com.example.away.dto.AssistidoUpdateRequest;
import com.example.away.exception.*;
import com.example.away.model.*;
import com.example.away.repository.AssistidoRepository;
import com.example.away.service.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TESTES COMPLETOS DE INTEGRAÇÃO - AssistidoController (Cobertura Total)")
class AssistidoIntegrationTest {

    @Mock private AssistidoRepository assistidoRepository;
    @Mock private AssistidoService assistidoService;
    @Mock private PessoaService pessoaService;
    @Mock private EnderecoService enderecoService;

    @InjectMocks private AssistidoController assistidoController;

    private Assistido assistido;
    private Pessoa pessoa;
    private Endereco endereco;
    private AssistidoCreateRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        endereco = new Endereco();
        endereco.setLogradouro("Rua das Flores");
        endereco.setCep("12345-678");

        pessoa = new Pessoa();
        pessoa.setNome("João");
        pessoa.setSegundoNome("Silva");
        pessoa.setCpf("12345678909");
        pessoa.setTelefone("45912345678");
        pessoa.setEndereco(endereco);

        assistido = new Assistido();
        assistido.setPessoa(pessoa);
        assistido.setNumAuto("123");
        assistido.setNumProcesso("456");
        assistido.setObservacao("Teste OK");

        AssistidoCreateRequest.EnderecoData endDTO = new AssistidoCreateRequest.EnderecoData();
        endDTO.setLogradouro("Rua das Flores");

        AssistidoCreateRequest.PessoaData pesDTO = new AssistidoCreateRequest.PessoaData();
        pesDTO.setNome("João");
        pesDTO.setSegundoNome("Silva");
        pesDTO.setCpf("12345678909");
        pesDTO.setTelefone("45912345678");
        pesDTO.setEndereco(endDTO);
        pesDTO.setDataNascimento("2020-10-10");

        request = new AssistidoCreateRequest();
        request.setPessoa(pesDTO);
        request.setNumAuto("123");
        request.setNumProcesso("456");
        request.setObservacao("Teste de sucesso");
    }

    // ---------------------------------------------------------------
    // TESTES DO MÉTODO SAVE
    // ---------------------------------------------------------------

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Sucesso com dados completos")
    void deveSalvarAssistidoComSucesso() {
        when(enderecoService.save(any())).thenReturn(endereco);
        when(pessoaService.save(any())).thenReturn(pessoa);
        when(assistidoService.save(any())).thenReturn(assistido);

        ResponseEntity<Map<String, Object>> response = assistidoController.save(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue((boolean) response.getBody().get("success"));
        assertEquals("Assistido cadastrado com sucesso!", response.getBody().get("message"));
        assertNotNull(response.getBody().get("assistido"));
    }

    @Test
    @DisplayName("SAVE - Deve lançar exceção quando request for nulo")
    void deveLancarExcecaoQuandoRequestForNulo() {
        BusinessException ex = assertThrows(BusinessException.class, () -> assistidoController.save(null));
        assertTrue(ex.getMessage().contains("Dados do assistido são obrigatórios"));
    }

    @Test
    @DisplayName("SAVE - Deve lançar exceção quando pessoa for nula")
    void deveLancarExcecaoQuandoPessoaForNula() {
        AssistidoCreateRequest req = new AssistidoCreateRequest();
        req.setPessoa(null);

        BusinessException ex = assertThrows(BusinessException.class, () -> assistidoController.save(req));
        assertEquals("Dados do assistido são obrigatórios", ex.getMessage());
    }

    @Test
    @DisplayName("SAVE - Deve lançar BusinessException ao ocorrer erro no serviço")
    void deveLancarBusinessExceptionAoSalvar() {
        when(enderecoService.save(any())).thenThrow(new RuntimeException("Falha no banco"));

        BusinessException ex = assertThrows(BusinessException.class, () -> assistidoController.save(request));
        assertTrue(ex.getMessage().contains("Erro ao cadastrar assistido"));
        assertTrue(ex.getMessage().contains("Falha no banco"));
    }

    // ---------------------------------------------------------------
    // TESTES DO MÉTODO FIND BY ID
    // ---------------------------------------------------------------

    @Test
    @DisplayName("FIND BY ID - Sucesso")
    void deveRetornarAssistidoPorId() {
        when(assistidoService.findById(1L)).thenReturn(assistido);

        ResponseEntity<Assistido> response = assistidoController.findById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("João", response.getBody().getPessoa().getNome());
    }

    @Test
    @DisplayName("FIND BY ID - Deve lançar ResourceNotFoundException quando não encontrado")
    void deveLancarExcecaoQuandoNaoEncontrarAssistido() {
        when(assistidoService.findById(999L)).thenReturn(null);
        assertThrows(ResourceNotFoundException.class, () -> assistidoController.findById(999L));
    }

    // ---------------------------------------------------------------
    // TESTES DO MÉTODO FIND ALL
    // ---------------------------------------------------------------

    @Test
    @DisplayName("FIND ALL - Deve retornar lista")
    void deveRetornarListaDeAssistidos() {
        when(assistidoService.findAll()).thenReturn(List.of(assistido));
        ResponseEntity<List<Assistido>> response = assistidoController.findAll();
        assertEquals(1, response.getBody().size());
    }

    // ---------------------------------------------------------------
    // TESTES DO MÉTODO UPDATE
    // ---------------------------------------------------------------

    @Test
    @DisplayName("UPDATE - Sucesso com todos os dados")
    void deveAtualizarComSucesso() throws ParseException {
        when(assistidoService.findById(1L)).thenReturn(assistido);
        when(assistidoService.update(any(), any())).thenReturn(assistido);

        AssistidoUpdateRequest req = new AssistidoUpdateRequest();
        req.setNumAuto("999");
        req.setNumProcesso("888");
        req.setObservacao("Atualizado");

        AssistidoUpdateRequest.PessoaUpdateDTO pessoaDTO = new AssistidoUpdateRequest.PessoaUpdateDTO();
        pessoaDTO.setNome("João Atualizado");
        pessoaDTO.setCpf("123");
        pessoaDTO.setTelefone("99999");
        pessoaDTO.setSegundoNome("Atualizado");
        pessoaDTO.setDataNascimento("2020-01-01");

        AssistidoUpdateRequest.EnderecoUpdateDTO endDTO = new AssistidoUpdateRequest.EnderecoUpdateDTO();
        endDTO.setLogradouro("Rua Nova");
        endDTO.setCep("12345");
        endDTO.setBairro("Centro");
        endDTO.setCidade("CidadeX");
        endDTO.setEstado("PR");
        endDTO.setNumero(10);
        pessoaDTO.setEndereco(endDTO);

        req.setPessoa(pessoaDTO);

        when(enderecoService.save(any())).thenReturn(endereco);
        when(pessoaService.save(any())).thenReturn(pessoa);

        ResponseEntity<Map<String, Object>> response = assistidoController.update(1L, req);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody().get("success"));
        assertEquals("Assistido atualizado com sucesso!", response.getBody().get("message"));
    }

    @Test
    @DisplayName("UPDATE - Deve retornar NOT_FOUND se assistido não existir")
    void deveRetornarNotFoundAoAtualizarInexistente() {
        when(assistidoService.findById(999L)).thenReturn(null);

        AssistidoUpdateRequest req = new AssistidoUpdateRequest();
        req.setNumAuto("123");
        req.setNumProcesso("456");

        ResponseEntity<Map<String, Object>> response = assistidoController.update(999L, req);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(false, response.getBody().get("success"));
        assertEquals("Assistido não encontrado", response.getBody().get("message"));
    }

    @Test
    @DisplayName("UPDATE - Deve tratar exceção e retornar BAD_REQUEST")
    void deveRetornarBadRequestAoOcorrerErro() {
        when(assistidoService.findById(1L)).thenThrow(new RuntimeException("Erro interno"));

        ResponseEntity<Map<String, Object>> response = assistidoController.update(1L, new AssistidoUpdateRequest());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(((String) response.getBody().get("message")).contains("Erro ao atualizar assistido"));
    }

    @Test
    @DisplayName("UPDATE - Deve lidar com pessoa nula")
    void deveAtualizarComPessoaNulaSemErro() {
        when(assistidoService.findById(1L)).thenReturn(assistido);
        when(assistidoService.update(any(), any())).thenReturn(assistido);

        AssistidoUpdateRequest req = new AssistidoUpdateRequest();
        req.setPessoa(null);
        req.setNumAuto("123");

        ResponseEntity<Map<String, Object>> response = assistidoController.update(1L, req);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("UPDATE - Deve lidar com pessoa sem endereço")
    void deveAtualizarPessoaSemEndereco() {
        when(assistidoService.findById(1L)).thenReturn(assistido);
        when(assistidoService.update(any(), any())).thenReturn(assistido);

        AssistidoUpdateRequest req = new AssistidoUpdateRequest();
        AssistidoUpdateRequest.PessoaUpdateDTO pessoaDTO = new AssistidoUpdateRequest.PessoaUpdateDTO();
        pessoaDTO.setEndereco(null);
        req.setPessoa(pessoaDTO);

        ResponseEntity<Map<String, Object>> response = assistidoController.update(1L, req);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @DisplayName("UPDATE - Deve lidar com erro no enderecoService.save() e capturar exceção no bloco catch interno")
    void deveTratarErroNoEnderecoServiceSave() {
        when(assistidoService.findById(1L)).thenReturn(assistido);
        when(enderecoService.save(any())).thenThrow(new RuntimeException("Falha ao salvar endereço"));

        AssistidoUpdateRequest req = new AssistidoUpdateRequest();
        AssistidoUpdateRequest.PessoaUpdateDTO pessoaDTO = new AssistidoUpdateRequest.PessoaUpdateDTO();
        pessoaDTO.setEndereco(new AssistidoUpdateRequest.EnderecoUpdateDTO());
        req.setPessoa(pessoaDTO);

        ResponseEntity<Map<String, Object>> response = assistidoController.update(1L, req);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // ---------------------------------------------------------------
    // TESTES DO MÉTODO DELETE
    // ---------------------------------------------------------------

    @Test
    @DisplayName("DELETE - Sucesso ao deletar")
    void deveDeletarComSucesso() {
        when(assistidoService.findById(1L)).thenReturn(assistido);
        doNothing().when(assistidoService).delete(1L);

        ResponseEntity<Map<String, Object>> response = assistidoController.delete(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Assistido excluído com sucesso!", response.getBody().get("message"));
    }

    @Test
    @DisplayName("DELETE - Deve retornar NOT_FOUND quando assistido não existe")
    void deveRetornarNotFoundQuandoAssistidoNaoExiste() {
        when(assistidoService.findById(999L)).thenReturn(null);
        ResponseEntity<Map<String, Object>> response = assistidoController.delete(999L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Assistido não encontrado", response.getBody().get("message"));
    }

    @Test
    @DisplayName("DELETE - Deve capturar exceção e retornar BAD_REQUEST")
    void deveTratarErroAoDeletar() {
        when(assistidoService.findById(1L)).thenReturn(assistido);
        doThrow(new RuntimeException("Erro interno")).when(assistidoService).delete(1L);

        ResponseEntity<Map<String, Object>> response = assistidoController.delete(1L);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(((String) response.getBody().get("message")).contains("Erro ao excluir assistido"));
    }

    // ---------------------------------------------------------------
    // TESTES DOS MÉTODOS DE BUSCA AUTOMÁTICA
    // ---------------------------------------------------------------

    @Test
    @DisplayName("BUSCAR POR NUM AUTO - Deve retornar lista")
    void deveBuscarPorNumAuto() {
        when(assistidoService.buscarPorNumAuto("123")).thenReturn(List.of(assistido));
        ResponseEntity<List<Assistido>> response = assistidoController.buscarPorNumAuto("123");
        assertEquals(1, response.getBody().size());
    }

    @Test
    @DisplayName("BUSCAR POR NUM PROCESSO - Deve retornar lista")
    void deveBuscarPorNumProcesso() {
        when(assistidoService.buscarPorNumProcesso("456")).thenReturn(List.of(assistido));
        ResponseEntity<List<Assistido>> response = assistidoController.buscarPorNumProcesso("456");
        assertEquals(1, response.getBody().size());
    }

    @Test
    @DisplayName("BUSCAR POR NUM AUTO - Deve retornar lista vazia")
    void deveRetornarListaVaziaNumAuto() {
        when(assistidoService.buscarPorNumAuto("000")).thenReturn(List.of());
        ResponseEntity<List<Assistido>> response = assistidoController.buscarPorNumAuto("000");
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    @DisplayName("BUSCAR POR NUM PROCESSO - Deve retornar lista vazia")
    void deveRetornarListaVaziaNumProcesso() {
        when(assistidoService.buscarPorNumProcesso("000")).thenReturn(List.of());
        ResponseEntity<List<Assistido>> response = assistidoController.buscarPorNumProcesso("000");
        assertTrue(response.getBody().isEmpty());
    }
}
