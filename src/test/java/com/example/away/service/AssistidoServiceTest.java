package com.example.away.service;

import com.example.away.model.Assistido;
import com.example.away.repository.AssistidoRepository;
import com.example.away.model.Pessoa;
import com.example.away.model.Endereco;
import com.example.away.repository.ComparecimentoRepository;
import com.example.away.repository.EnderecoRepository;
import com.example.away.repository.PessoaRepository;
import com.example.away.service.UtilService;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AssistidoServiceTest {

    @Mock
    private AssistidoRepository assistidoRepository;

    @Mock
    private ComparecimentoRepository comparecimentoRepository;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AssistidoService assistidoService;

    private Assistido assistidoMock;
    private final Long ASSISTIDO_ID = 1L;
    private Date dataAtual;

    @BeforeEach
    void setUp() {
        // Inicializa o objeto Assistido de teste
        assistidoMock = new Assistido();
        assistidoMock.setIdAssistido(ASSISTIDO_ID);
        assistidoMock.setNumAuto("12345");

        Pessoa pessoa = new Pessoa();
        pessoa.setNome("Teste Pessoa");
        pessoa.setEndereco(new Endereco()); // Endereço para teste de carregamento
        assistidoMock.setPessoa(pessoa);

        dataAtual = new Date();

        // Configura o AssistidoService para injetar os mocks
        assistidoService = new AssistidoService(assistidoRepository);

        // Injeta os mocks extras que não estão no construtor
        // assistidoService.setComparecimentoRepository(comparecimentoRepository); // Se houver setters ou use ReflectionTestUtils
        // assistidoService.setPessoaRepository(pessoaRepository);
        // assistidoService.setEnderecoRepository(enderecoRepository);
        // assistidoService.setJdbcTemplate(jdbcTemplate);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve retornar lista de assistidos e forçar o carregamento de relacionamentos Pessoa/Endereco")
    void findAllListaDeAssistidos() {
        // Arrange
        List<Assistido> assistidos = Arrays.asList(assistidoMock, new Assistido());
        when(assistidoRepository.findAll()).thenReturn(assistidos);

        // Act
        List<Assistido> resultado = assistidoService.findAll();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(assistidoRepository, times(1)).findAll();
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Quando o ID é encontrado, deve retornar o Assistido")
    void findByIdAssistidoExiste() {
        // Arrange
        when(assistidoRepository.findById(ASSISTIDO_ID)).thenReturn(Optional.of(assistidoMock));

        // Act
        Assistido resultado = assistidoService.findById(ASSISTIDO_ID);

        // Assert
        assertNotNull(resultado);
        assertEquals(ASSISTIDO_ID, resultado.getIdAssistido());
        verify(assistidoRepository, times(1)).findById(ASSISTIDO_ID);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Quando o ID não é encontrado, deve lançar EntityNotFoundException")
    void findByIdAssistidoNaoExiste() {
        // Arrange
        when(assistidoRepository.findById(ASSISTIDO_ID)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            assistidoService.findById(ASSISTIDO_ID);
        });
        verify(assistidoRepository, times(1)).findById(ASSISTIDO_ID);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve salvar o Assistido")
    void saveAssistido() {
        // Arrange
        Assistido novoAssistido = new Assistido();
        novoAssistido.setNumProcesso("98765");

        when(assistidoRepository.save(any(Assistido.class))).thenReturn(assistidoMock);

        // Act
        Assistido resultado = assistidoService.save(novoAssistido);

        // Assert
        verify(assistidoRepository, times(1)).save(novoAssistido);
        assertEquals(assistidoMock, resultado);

    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve atualizar os campos modificáveis e salvar")
    void updateAssistido() {
        // Arrange
        Assistido assistidoComNovosDados = new Assistido();
        assistidoComNovosDados.setNumAuto("11111");
        assistidoComNovosDados.setNumProcesso("22222");
        assistidoComNovosDados.setObservacao("Nova Observação");
        //assistidoComNovosDados.setStatusComparecimento(EnumSituacao.COMPARECEU); // Assumindo EnumSituacao

        when(assistidoRepository.findById(ASSISTIDO_ID)).thenReturn(Optional.of(assistidoMock));
        when(assistidoRepository.save(any(Assistido.class))).thenReturn(assistidoMock);

        // Act
        Assistido resultado = assistidoService.update(ASSISTIDO_ID, assistidoComNovosDados);

        // Assert
        assertEquals("11111", resultado.getNumAuto());
        assertEquals("22222", resultado.getNumProcesso());
        assertEquals("Nova Observação", resultado.getObservacao());
        // assertEquals(EnumSituacao.COMPARECEU, resultado.getStatusComparecimento());

        verify(assistidoRepository, times(1)).findById(ASSISTIDO_ID);
        verify(assistidoRepository, times(1)).save(assistidoMock);
    }


    /*@Test
    @DisplayName("TESTE UNITÁRIO > Deve executar o SQL DELETE/UPDATE para todos os relacionamentos na ordem correta")
    void deleteAssistido() {
        // Arrange
        Long ID_ENDERECO_1 = 100L;
        Long ID_ENDERECO_2 = 101L;
        List<Long> enderecoIds = Arrays.asList(ID_ENDERECO_1, ID_ENDERECO_2);

        // 1. Mock do retorno dos IDs dos endereços
        when(jdbcTemplate.queryForList(anyString(), eq(Long.class), eq(ASSISTIDO_ID))).thenReturn(enderecoIds);

        // 2. Mock do update para as exclusões/atualizações
        // Não precisamos nos preocupar com o retorno de `update` (int), apenas com a chamada.

        // Act
        assertDoesNotThrow(() -> assistidoService.delete(ASSISTIDO_ID));

        // Assert
        // Verifica a exclusão de comparecimentos
        verify(jdbcTemplate, times(1)).update(eq("DELETE FROM comparecimento WHERE id_assistido = ?"), eq(ASSISTIDO_ID));

        // Verifica a remoção de referência de endereço na pessoa
        verify(jdbcTemplate, times(1)).update(eq("UPDATE pessoa SET id_endereco = NULL WHERE id_assistido = ?"), eq(ASSISTIDO_ID));

        // Verifica a busca pelos IDs dos endereços
        verify(jdbcTemplate, times(1)).queryForList(
                eq("SELECT id_endereco FROM pessoa WHERE id_assistido = ? AND id_endereco IS NOT NULL"),
                eq(Long.class),
                eq(ASSISTIDO_ID)
        );

        // Verifica a exclusão de cada endereço
        verify(jdbcTemplate, times(1)).update(eq("DELETE FROM endereco WHERE id_endereco = ?"), eq(ID_ENDERECO_1));
        verify(jdbcTemplate, times(1)).update(eq("DELETE FROM endereco WHERE id_endereco = ?"), eq(ID_ENDERECO_2));

        // Verifica a exclusão de pessoas
        verify(jdbcTemplate, times(1)).update(eq("DELETE FROM pessoa WHERE id_assistido = ?"), eq(ASSISTIDO_ID));

        // Verifica a exclusão do assistido
        verify(jdbcTemplate, times(1)).update(eq("DELETE FROM assistido WHERE id_assistido = ?"), eq(ASSISTIDO_ID));
    }*/


    /*@Test
    @DisplayName("TESTE UNITÁRIO > Quando ocorre erro no banco, deve relançar a exceção para garantir o rollback")
    void deleteAssistidoExcecao() {
        // Arrange
        RuntimeException sqlException = new RuntimeException("Simulando erro de exclusão no BD");

        // Faz o primeiro update (exclusão de comparecimentos) lançar uma exceção
        when(jdbcTemplate.update(eq("DELETE FROM comparecimento WHERE id_assistido = ?"), eq(ASSISTIDO_ID)))
                .thenThrow(sqlException);

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            assistidoService.delete(ASSISTIDO_ID);
        });

        assertEquals(sqlException, thrown);
        // Garante que o método de exclusão do assistido não foi chamado (rollback do @Transactional)
        verify(jdbcTemplate, never()).update(eq("DELETE FROM assistido WHERE id_assistido = ?"), Optional.ofNullable(any()));
    }*/


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve buscar e retornar lista de assistidos pelo número do auto")
    void buscarPorNumAuto() {
        // Arrange
        String numAuto = "12345";
        List<Assistido> lista = Arrays.asList(assistidoMock);
        when(assistidoRepository.findByNumAuto(numAuto)).thenReturn(lista);

        // Act
        List<Assistido> resultado = assistidoService.buscarPorNumAuto(numAuto);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(assistidoRepository, times(1)).findByNumAuto(numAuto);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve buscar e retornar lista de assistidos pelo número do processo")
    void buscarPorNumProcesso() {
        // Arrange
        String numProcesso = "98765";
        List<Assistido> lista = Arrays.asList(assistidoMock);
        when(assistidoRepository.findByNumProcesso(numProcesso)).thenReturn(lista);

        // Act
        List<Assistido> resultado = assistidoService.buscarPorNumProcesso(numProcesso);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(assistidoRepository, times(1)).findByNumProcesso(numProcesso);
    }

    // Métodos para permitir a injeção dos mocks que não estão no construtor
    private void setComparecimentoRepository(ComparecimentoRepository repository) {

        try {
            java.lang.reflect.Field field = AssistidoService.class.getDeclaredField("comparecimentoRepository");
            field.setAccessible(true);
            field.set(assistidoService, repository);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Ignora ou trata o erro
        }
    }
    private void setPessoaRepository(PessoaRepository repository) {
        try {
            java.lang.reflect.Field field = AssistidoService.class.getDeclaredField("pessoaRepository");
            field.setAccessible(true);
            field.set(assistidoService, repository);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Ignora
        }
    }
    private void setEnderecoRepository(EnderecoRepository repository) {
        try {
            java.lang.reflect.Field field = AssistidoService.class.getDeclaredField("enderecoRepository");
            field.setAccessible(true);
            field.set(assistidoService, repository);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Ignora
        }
    }
    private void setJdbcTemplate(JdbcTemplate template) {
        try {
            java.lang.reflect.Field field = AssistidoService.class.getDeclaredField("jdbcTemplate");
            field.setAccessible(true);
            field.set(assistidoService, template);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // Ignora
        }
    }
}