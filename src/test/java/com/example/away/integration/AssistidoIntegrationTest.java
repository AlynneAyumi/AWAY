package com.example.away.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.example.away.controller.AssistidoController;
import com.example.away.dto.AssistidoCreateRequest;
import com.example.away.exception.BusinessException;
import com.example.away.model.Assistido;
import com.example.away.model.Endereco;
import com.example.away.model.Pessoa;
import com.example.away.repository.AssistidoRepository;
import com.example.away.service.AssistidoService;
import com.example.away.service.EnderecoService;
import com.example.away.service.PessoaService;

@DisplayName("TESTES DE INTEGRAÇÃO - AssistidoController")
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Cenário de sucesso ao criar Assistido")
    void testeAssistidoCriadoComSucesso() {

        // --- Cenário: criar Assistido ---
        Endereco endereco = new Endereco();
        endereco.setLogradouro("Rua das Flores");

        Pessoa pessoa = new Pessoa();
        pessoa.setNome("João");
        pessoa.setSegundoNome("Maria");
        pessoa.setCpf("12345678909");
        pessoa.setTelefone("45912345678");
        pessoa.setEndereco(endereco);

        Assistido assistido = new Assistido();
        assistido.setPessoa(pessoa);

        // --- DTO correto para o controller ---
        AssistidoCreateRequest.EnderecoData enderecoData = new AssistidoCreateRequest.EnderecoData();
        enderecoData.setLogradouro("Rua das Flores");

        AssistidoCreateRequest.PessoaData pessoaData = new AssistidoCreateRequest.PessoaData();
        pessoaData.setNome("João");
        pessoaData.setSegundoNome("Maria");
        pessoaData.setCpf("12345678909");
        pessoaData.setTelefone("45912345678");
        pessoaData.setEndereco(enderecoData);

        AssistidoCreateRequest request = new AssistidoCreateRequest();
        request.setPessoa(pessoaData);
        request.setNumAuto("123");
        request.setNumProcesso("456");
        request.setObservacao("Teste observação");

        // --- Mocks necessários ---
        when(assistidoService.save(any(Assistido.class))).thenReturn(assistido);
        when(assistidoRepository.save(any(Assistido.class))).thenReturn(assistido);
        when(pessoaService.save(any(Pessoa.class))).thenReturn(pessoa);

        // --- Chamada do controller ---
        ResponseEntity<Map<String, Object>> response = assistidoController.save(request);

        // --- Verificações ---
        assertNotNull(response, "Response não deve ser nulo");
        Map<String, Object> body = response.getBody();
        assertNotNull(body, "Body não deve ser nulo");

        // O controller provavelmente retorna o Assistido em "assistido"
        Assistido resultado = (Assistido) body.get("assistido");
        assertNotNull(resultado, "Assistido não deve ser nulo");
        assertNotNull(resultado.getPessoa(), "Pessoa não deve ser nula");

        // --- Asserções dos dados ---
        assertEquals("João", resultado.getPessoa().getNome(), "Nome deve ser João");
        assertEquals("Foz do Iguaçu", resultado.getPessoa().getEndereco().getCidade(), "Cidade deve ser Foz do Iguaçu");
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve lançar exceção quando request é nulo")
    void deveLancarExcecaoQuandoRequestForNulo() {
        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(
        BusinessException.class,
        () -> assistidoController.save(null)
        );
        assertEquals("Dados do assistido são obrigatórios", exception.getMessage());
    }
}
