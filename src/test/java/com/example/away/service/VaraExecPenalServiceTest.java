package com.example.away.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.away.model.VaraExecPenal;
import com.example.away.repository.VaraExecPenalRepository;
import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class VaraExecPenalServiceTest {

    // Injeta a classe a ser testada, e injeta os Mocks
    @InjectMocks
    private VaraExecPenalService varaExecPenalService;

    // Cria um mock para a dependência VaraExecPenalRepository
    @Mock
    private VaraExecPenalRepository varaPenalRepository;

    private VaraExecPenal varaPenal;
    private final Long id_existente = 20L;
    private final Long id_nao_existente = 99L;

    // Configuração inicial antes de cada teste
    @BeforeEach
    void setUp() {
        // Inicializa um objeto VaraExecPenal
        varaPenal = new VaraExecPenal();
        varaPenal.setIdVaraExecPenal(id_existente);
        varaPenal.setNome("1ª VEP");
        varaPenal.setDescricao("Primeira Vara de Execução Penal");
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve retornar uma lista de VaraExecPenal")
    void findAll_deveRetornarListaDeVaras() {
        // Arrange
        VaraExecPenal v2 = new VaraExecPenal();
        v2.setIdVaraExecPenal(21L);
        List<VaraExecPenal> listaEsperada = Arrays.asList(varaPenal, v2);

        // Simula o comportamento do repositório
        when(varaPenalRepository.findAll()).thenReturn(listaEsperada);

        // Act
        List<VaraExecPenal> listaAtual = varaExecPenalService.findAll();

        // Assert
        assertNotNull(listaAtual);
        assertEquals(2, listaAtual.size());
        assertEquals(listaEsperada, listaAtual);

        // Verifica se o método do repositório foi chamado pelo menos uma vez
        verify(varaPenalRepository, times(1)).findAll();
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve retornar VaraExecPenal quando o id for encontrado")
    void findByIdVaraExecExiste() {
        // Arrange
        when(varaPenalRepository.findById(id_existente)).thenReturn(Optional.of(varaPenal));

        // Act
        VaraExecPenal resultado = varaExecPenalService.findById(id_existente);

        // Assert
        assertNotNull(resultado);
        assertEquals(id_existente, resultado.getIdVaraExecPenal());
        assertEquals("1ª VEP", resultado.getNome());

        // Verifica se o método do repositório foi chamado
        verify(varaPenalRepository, times(1)).findById(id_existente);
    }

    @Test
    @DisplayName("TESTE UNITÁRIO > Deve lançar EntityNotFoundException quando o id não for encontrado")
    void findByIdVaraExecNaoExiste() {
        // Arrange
        when(varaPenalRepository.findById(id_nao_existente)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> varaExecPenalService.findById(id_nao_existente));

        // Verifica se o método do repositório foi chamado uma vez
        verify(varaPenalRepository, times(1)).findById(id_nao_existente);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve salvar e retornar a nova VaraExecPenal")
    void saveVaraExec() {
        // Arrange
        VaraExecPenal novaVara = new VaraExecPenal();
        novaVara.setNome("2ª VEP");
        novaVara.setDescricao("Segunda Vara de Execução Penal");

        // Mock do comportamento do save do repositório
        when(varaPenalRepository.save(any(VaraExecPenal.class))).thenReturn(novaVara);

        // Act
        VaraExecPenal resultado = varaExecPenalService.save(novaVara);

        // Assert
        assertNotNull(resultado);

        // Verifica se o método save foi chamado
        verify(varaPenalRepository, times(1)).save(novaVara);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve atualizar e retornar a VaraExecPenal com os novos dados")
    void updateVaraExec() {
        // Arrange
        VaraExecPenal dadosNovos = new VaraExecPenal();
        dadosNovos.setNome("3ª VEP - Modificada");
        dadosNovos.setDescricao("Terceira Vara de Execução Penal");

        VaraExecPenal varaOriginal = new VaraExecPenal();
        varaOriginal.setIdVaraExecPenal(id_existente);
        varaOriginal.setNome("3ª VEP");
        varaOriginal.setDescricao("Terceira Vara");

        // Mock do findById
        when(varaPenalRepository.findById(id_existente)).thenReturn(Optional.of(varaOriginal));

        // Mock do save
        when(varaPenalRepository.save(any(VaraExecPenal.class))).thenReturn(varaOriginal);

        // Act
        VaraExecPenal resultado = varaExecPenalService.update(id_existente, dadosNovos);

        // Assert
        assertNotNull(resultado);

        // Verifica se os campos foram atualizados no objeto 'update' (varaOriginal)
        assertEquals("3ª VEP - Modificada", resultado.getNome());
        assertEquals("Terceira Vara de Execução Penal", resultado.getDescricao());

        // Verifica se os métodos foram chamados
        verify(varaPenalRepository, times(1)).findById(id_existente);
        verify(varaPenalRepository, times(1)).save(varaOriginal);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve lançar EntityNotFoundException quando o id não for encontrado")
    void updateVaraExecNaoExiste() {
        // Arrange
        VaraExecPenal dadosNovos = new VaraExecPenal();
        when(varaPenalRepository.findById(id_nao_existente)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class,
                () -> varaExecPenalService.update(id_nao_existente, dadosNovos));

        verify(varaPenalRepository, times(1)).findById(id_nao_existente);

        // Garante que o save não é chamado nenhuma vez
        verify(varaPenalRepository, never()).save(any());
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve deletar a VaraExecPenal quando o id for encontrado")
    void deleteVaraExec() {
        // Arrange
        // Mock do findById
        when(varaPenalRepository.findById(id_existente)).thenReturn(Optional.of(varaPenal));

        // O método delete do repositório é void
        doNothing().when(varaPenalRepository).delete(varaPenal);

        // Act
        varaExecPenalService.delete(id_existente);

        // Assert
        verify(varaPenalRepository, times(1)).findById(id_existente);
        verify(varaPenalRepository, times(1)).delete(varaPenal);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve lançar EntityNotFoundException no delete quando o id não for encontrado")
    void deleteVaraExecNaoExiste() {
        // Arrange
        when(varaPenalRepository.findById(id_nao_existente)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> varaExecPenalService.delete(id_nao_existente));

        // Garante que o delete não é chamado
        verify(varaPenalRepository, never()).delete(any());
    }
}
