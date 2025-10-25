package com.example.away.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.away.model.Endereco;
import com.example.away.repository.EnderecoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

// Habilita o Mockito para JUnit 5
@ExtendWith(MockitoExtension.class)
class EnderecoServiceTest {

    // Dependência mockada
    @Mock
    private EnderecoRepository enderecoRepository;

    // Classe a ser testada, com o mock injetado
    @InjectMocks
    private EnderecoService enderecoService;

    private Endereco enderecoMock;
    private final Long ENDERECO_ID = 1L;
    private final String CEP_MOCK = "80000-000";

    @BeforeEach
    void setUp() {
        enderecoMock = new Endereco();
        enderecoMock.setIdEndereco(ENDERECO_ID);
        enderecoMock.setCep(CEP_MOCK);
        enderecoMock.setCidade("Curitiba");
        enderecoMock.setLogradouro("Rua das Flores");
    }

    // Usaremos esta data fixa para mockar o UtilService
    private Date getFixedDate() {
        // Retorna uma Date a partir de um LocalDate para garantir que a hora seja 00:00:00
        // e evitar problemas de comparação de milissegundos.
        return Date.from(LocalDate.of(2025, 10, 24).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    // Captura o LocalDate que será comparado no teste
    private LocalDate getFixedLocalDate() {
        return LocalDate.of(2025, 10, 24);
    }

    // Tipo java.util.Date na Entity, mas  compara com LocalDate para evitar falhas de tempo/fuso horário.
    private void assertDateEqualsLocalDate(LocalDate expectedLocalDate, Date actualDate) {
        LocalDate actualLocalDate = actualDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        assertEquals(expectedLocalDate, actualLocalDate);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve retornar todos os Enderecos")
    void findAll_DeveRetornarLista() {
        // Arrange
        List<Endereco> listaMocks = Arrays.asList(enderecoMock, new Endereco());
        when(enderecoRepository.findAll()).thenReturn(listaMocks);

        // Act
        List<Endereco> resultado = enderecoService.findAll();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        verify(enderecoRepository).findAll();
    }

    @Test
    @DisplayName("TESTE UNITÁRIO > Deve retornar Endereco quando encontrado por ID")
    void findById_QuandoEncontrado() {
        // Arrange
        when(enderecoRepository.findById(ENDERECO_ID)).thenReturn(Optional.of(enderecoMock));

        // Act
        Endereco resultado = enderecoService.findById(ENDERECO_ID);

        // Assert
        assertNotNull(resultado);
        assertEquals(ENDERECO_ID, resultado.getIdEndereco());
        verify(enderecoRepository).findById(ENDERECO_ID);
    }

    @Test
    @DisplayName("TESTE UNITÁRIO > Deve lançar EntityNotFoundException quando ID não encontrado")
    void findById_QuandoNaoEncontrado() {
        // Arrange
        when(enderecoRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            enderecoService.findById(99L);
        });
        verify(enderecoRepository).findById(99L);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve salvar Endereco e preencher campos de auditoria")
    void save_ComSucesso() {
        // Arrange
        Date dataFixa = getFixedDate();
        Endereco novoEndereco = new Endereco();
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(novoEndereco);

        // Act
        enderecoService.save(novoEndereco);

        // Assert
        ArgumentCaptor<Endereco> enderecoCaptor = ArgumentCaptor.forClass(Endereco.class);
        verify(enderecoRepository).save(enderecoCaptor.capture());

        Endereco enderecoSalvo = enderecoCaptor.getValue();

        assertEquals(1, enderecoSalvo.getCreatedBy());
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve atualizar os campos e a data de auditoria de um Endereco")
    void update_ComSucesso() {
        // Dados recebidos para atualização
        Endereco dadosParaAtualizar = new Endereco();
        dadosParaAtualizar.setCep("99999-999");
        dadosParaAtualizar.setCidade("Nova Cidade");

        // Mock para findById
        when(enderecoRepository.findById(ENDERECO_ID)).thenReturn(Optional.of(enderecoMock));
        // Mock para save
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(enderecoMock);

        // Act
        enderecoService.update(ENDERECO_ID, dadosParaAtualizar);

        // Assert
        ArgumentCaptor<Endereco> captor = ArgumentCaptor.forClass(Endereco.class);
        verify(enderecoRepository).save(captor.capture());

        Endereco enderecoAtualizado = captor.getValue();

        // Verifica a atualização dos dados
        assertEquals("99999-999", enderecoAtualizado.getCep());
        assertEquals("Nova Cidade", enderecoAtualizado.getCidade());

        // Verificamos que findById e save foram chamados
        verify(enderecoRepository).findById(ENDERECO_ID);
        verify(enderecoRepository).save(enderecoAtualizado);
    }

    @Test
    @DisplayName("TESTE UNITÁRIO > Deve deletar Endereco com sucesso quando encontrado")
    void delete_ComSucesso() {
        // Arrange
        when(enderecoRepository.findById(ENDERECO_ID)).thenReturn(Optional.of(enderecoMock));

        // Não precisamos mockar o retorno de delete, pois ele é void
        doNothing().when(enderecoRepository).delete(enderecoMock);

        // Act
        enderecoService.delete(ENDERECO_ID);

        // Assert
        verify(enderecoRepository).findById(ENDERECO_ID);
        verify(enderecoRepository).delete(enderecoMock);
    }

    @Test
    @DisplayName("TESTE UNITÁRIO > Deve lançar EntityNotFoundException se tentar deletar Endereco inexistente")
    void delete_QuandoNaoEncontrado() {
        // Arrange
        when(enderecoRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> {
            enderecoService.delete(99L);
        });
        // Garante que delete() NUNCA foi chamado no mock
        verify(enderecoRepository, never()).delete(any(Endereco.class));
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > buscarPorCep: Deve retornar Endereco quando CEP for encontrado")
    void buscarPorCep_ComSucesso() {
        // Arrange
        when(enderecoRepository.findByCep(CEP_MOCK)).thenReturn(Optional.of(enderecoMock));

        // Act
        Endereco resultado = enderecoService.buscarPorCep(CEP_MOCK);

        // Assert
        assertNotNull(resultado);
        assertEquals(CEP_MOCK, resultado.getCep());
    }

    @Test
    @DisplayName("TESTE UNITÁRIO > buscarPorCep: Deve lançar EntityNotFoundException quando CEP não for encontrado")
    void buscarPorCep_NaoEncontrado() {
        // Arrange
        String cepInexistente = "00000-000";
        when(enderecoRepository.findByCep(cepInexistente)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException e = assertThrows(EntityNotFoundException.class, () -> {
            enderecoService.buscarPorCep(cepInexistente);
        });

        assertTrue(e.getMessage().contains(cepInexistente));
    }

    // ---

    @Test
    @DisplayName("TESTE UNITÁRIO > buscarPorCidade: Deve retornar Endereco quando Cidade for encontrada")
    void buscarPorCidade_ComSucesso() {
        // Arrange
        String cidade = "Curitiba";
        when(enderecoRepository.findByCidade(cidade)).thenReturn(Optional.of(enderecoMock));

        // Act
        Endereco resultado = enderecoService.buscarPorCidade(cidade);

        // Assert
        assertNotNull(resultado);
        assertEquals(cidade, resultado.getCidade());
    }

    @Test
    @DisplayName("TESTE UNITÁRIO > buscarPorCidade: Deve lançar IllegalArgumentException quando Cidade não for encontrada")
    void buscarPorCidade_NaoEncontrada() {
        // Arrange
        String cidadeInexistente = "Inexistente";
        when(enderecoRepository.findByCidade(cidadeInexistente)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            enderecoService.buscarPorCidade(cidadeInexistente);
        });

        assertTrue(e.getMessage().contains(cidadeInexistente));
    }
}