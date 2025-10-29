package com.example.away.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.example.away.model.Pessoa;
import com.example.away.repository.PessoaRepository;
import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// Mocks para classes não fornecidas no escopo deste teste
class Endereco {}
class Assistido {}
class Usuario {}

@ExtendWith(MockitoExtension.class)
class PessoaServiceTest {

    @InjectMocks
    private PessoaService pessoaService;

    @Mock
    private PessoaRepository pessoaRepository;

    private Pessoa pessoa;
    private final Long id_existente = 15L;
    private final Long id_nao_existente = 99L;
    private final String cpf_existente = "12345678900";
    private final String cpf_nao_existente = "00011122233";

    // Configuração inicial antes de cada teste
    @BeforeEach
    void setUp() {
        // Inicializa um objeto Pessoa
        pessoa = new Pessoa();
        pessoa.setIdPessoa(id_existente);
        pessoa.setNome("João");
        pessoa.setSegundoNome("Silva");
        pessoa.setCpf(cpf_existente);
        pessoa.setTelefone("999999999");
        pessoa.setEmail("joao.silva@teste.com");
        pessoa.setDataNascimento(new Date());
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve retornar uma lista de Pessoa")
    void findAllListaDePessoas() {
        // Arrange
        Pessoa p2 = new Pessoa();
        p2.setIdPessoa(16L);
        List<Pessoa> listaEsperada = Arrays.asList(pessoa, p2);

        // Simula o comportamento do repositório
        when(pessoaRepository.findAll()).thenReturn(listaEsperada);

        // Act
        List<Pessoa> listaAtual = pessoaService.findAll();

        // Assert
        assertNotNull(listaAtual);
        assertEquals(2, listaAtual.size());
        assertEquals(listaEsperada, listaAtual);

        // Verifica se o método do repositório foi chamado
        verify(pessoaRepository, times(1)).findAll();
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve retornar Pessoa quando o id for encontrado")
    void findByIdPessoaExiste() {
        // Arrange
        when(pessoaRepository.findById(id_existente)).thenReturn(Optional.of(pessoa));

        // Act
        Pessoa resultado = pessoaService.findById(id_existente);

        // Assert
        assertNotNull(resultado);
        assertEquals(id_existente, resultado.getIdPessoa());

        verify(pessoaRepository, times(1)).findById(id_existente);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve lançar EntityNotFoundException quando o id não for encontrado")
    void findByIdPessoaNaoExiste() {
        // Arrange
        when(pessoaRepository.findById(id_nao_existente)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> pessoaService.findById(id_nao_existente));

        verify(pessoaRepository, times(1)).findById(id_nao_existente);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve salvar e retornar a nova Pessoa")
    void savePessoa() {
        // Arrange
        Pessoa novaPessoa = new Pessoa();
        novaPessoa.setNome("Maria");

        // Mock do comportamento do save do repositório
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(novaPessoa);

        // Act
        Pessoa resultado = pessoaService.save(novaPessoa);

        // Assert
        assertNotNull(resultado);

        // Verifica se o método save foi chamado
        verify(pessoaRepository, times(1)).save(novaPessoa);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve atualizar dados de Pessoa")
    void updatePessoa() {
        // Arrange
        Date novaDataNascimento = new Date(System.currentTimeMillis() - 86400000);

        Pessoa dadosNovos = new Pessoa();
        dadosNovos.setNome("João Alterado");
        dadosNovos.setCpf("09876543210");
        dadosNovos.setSegundoNome("Santos");
        dadosNovos.setDataNascimento(novaDataNascimento);
        dadosNovos.setTelefone("888888888");

        // Mock do findById (retorna o objeto original que será modificado)
        when(pessoaRepository.findById(id_existente)).thenReturn(Optional.of(pessoa));

        // Mock do save (retorna o objeto modificado)
        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoa);

        // Act
        Pessoa resultado = pessoaService.update(id_existente, dadosNovos);

        // Assert
        assertNotNull(resultado);

        // Verifica se os campos foram atualizados no objeto 'update'/'pessoa'
        assertEquals("João Alterado", resultado.getNome());
        assertEquals("09876543210", resultado.getCpf());
        assertEquals("Santos", resultado.getSegundoNome());
        assertEquals(novaDataNascimento, resultado.getDataNascimento());
        assertEquals("888888888", resultado.getTelefone());

        // Verifica chamadas
        verify(pessoaRepository, times(1)).findById(id_existente);
        verify(pessoaRepository, times(1)).save(pessoa);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve deletar a Pessoa quando o id for encontrado")
    void deletePessoa() {
        // Arrange
        when(pessoaRepository.findById(id_existente)).thenReturn(Optional.of(pessoa));
        doNothing().when(pessoaRepository).delete(pessoa);

        // Act
        pessoaService.delete(id_existente);

        // Assert
        verify(pessoaRepository, times(1)).findById(id_existente);
        verify(pessoaRepository, times(1)).delete(pessoa);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve lançar EntityNotFoundException quando o id não for encontrado")
    void deletePessoaNaoExiste() {
        // Arrange
        when(pessoaRepository.findById(id_nao_existente)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> pessoaService.delete(id_nao_existente));
        verify(pessoaRepository, times(1)).findById(id_nao_existente);
        verify(pessoaRepository, never()).delete(any());
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve retornar Pessoa quando o CPF for encontrado")
    void buscarPessoaPorCpfExiste() {
        // Arrange
        when(pessoaRepository.findByCpfComJPQL(cpf_existente)).thenReturn(Optional.of(pessoa));

        // Act
        Pessoa resultado = pessoaService.buscarPessoaPorCpf(cpf_existente);

        // Assert
        assertNotNull(resultado);
        assertEquals(cpf_existente, resultado.getCpf());

        verify(pessoaRepository, times(1)).findByCpfComJPQL(cpf_existente);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve lançar NoSuchElementException quando o CPF não for encontrado")
    void buscarPessoaPorCpfNaoExiste() {
        // Arrange
        when(pessoaRepository.findByCpfComJPQL(cpf_nao_existente)).thenReturn(Optional.empty());

        // Act & Assert
        NoSuchElementException thrown = assertThrows(NoSuchElementException.class,
                () -> pessoaService.buscarPessoaPorCpf(cpf_nao_existente));

        assertEquals("Pessoa com CPF " + cpf_nao_existente + " não encontrada.", thrown.getMessage());

        verify(pessoaRepository, times(1)).findByCpfComJPQL(cpf_nao_existente);
    }
}