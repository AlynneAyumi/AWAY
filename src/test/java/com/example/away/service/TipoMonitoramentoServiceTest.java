package com.example.away.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.away.model.TipoMonitoramento;
import com.example.away.repository.TipoMonitoramentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TipoMonitoramentoServiceTest {

    // Injeta a classe a ser testada, e injeta os Mocks marcados com @Mock
    @InjectMocks
    private TipoMonitoramentoService tipoMonitoramentoService;

    // Cria um mock para a dependência
    @Mock
    private TipoMonitoramentoRepository monitoramentoRepository;

    // Mock para a classe utilitária estática
    private TipoMonitoramento monitoramento;
    private final Long id_existente = 1L;
    private final Long id_nao_existente = 99L;
    //private final Date data_mock = new Date(1633046400000L); // 2021-10-01 00:00:00 UTC

    // Método de configuração que é executado antes de cada teste
    @BeforeEach
    void setUp() {
        // Inicializa um objeto TipoMonitoramento para usar nos testes
        monitoramento = new TipoMonitoramento();
        monitoramento.setIdTipoMonitoramento(id_existente);
        monitoramento.setDescricao("Risco Alto");
        monitoramento.setFlagAtivo(true);
    }

    // Cenarios
    @Test
    @DisplayName("TESTE UNITÁRIO > Deve retornar uma lista de TipoMonitoramento quando findAll for chamado")
    void findAllListaDeMonitoramentos() {
        // Arrange
        TipoMonitoramento m2 = new TipoMonitoramento();
        m2.setIdTipoMonitoramento(2L);
        List<TipoMonitoramento> listaEsperada = Arrays.asList(monitoramento, m2);

        // Simula o comportamento do repositório
        when(monitoramentoRepository.findAll()).thenReturn(listaEsperada);

        // Act
        List<TipoMonitoramento> listaAtual = tipoMonitoramentoService.findAll();

        // Assert
        assertNotNull(listaAtual);
        assertEquals(2, listaAtual.size());
        assertEquals(listaEsperada, listaAtual);

        // Verifica se o método do repositório foi chamado pelo menos uma vez
        verify(monitoramentoRepository, times(1)).findAll();
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve retornar uma lista vazia quando não houver TipoMonitoramento")
    void findAllListaVazia() {
        // Arrange
        when(monitoramentoRepository.findAll()).thenReturn(List.of());

        // Act
        List<TipoMonitoramento> listaAtual = tipoMonitoramentoService.findAll();

        // Assert
        assertNotNull(listaAtual);
        assertTrue(listaAtual.isEmpty());

        // Verifica se o método do repositório foi chamado pelo menos uma vez
        verify(monitoramentoRepository, times(1)).findAll();
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve retornar TipoMonitoramento quando o id for encontrado")
    void findByIdMonitoramentoExiste() {
        // Arrange
        when(monitoramentoRepository.findById(id_existente)).thenReturn(Optional.of(monitoramento));

        // Act
        TipoMonitoramento resultado = tipoMonitoramentoService.findById(id_existente);

        // Assert
        assertNotNull(resultado);
        assertEquals(id_existente, resultado.getIdTipoMonitoramento());
        assertEquals("Risco Alto", resultado.getDescricao());

        verify(monitoramentoRepository, times(1)).findById(id_existente);
    }

    @Test
    @DisplayName("TESTE UNITÁRIO > Deve lançar EntityNotFoundException quando o id não for encontrado")
    void findByIdMonitoramentoNaoExiste() {
        // Arrange
        when(monitoramentoRepository.findById(id_nao_existente)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> tipoMonitoramentoService.findById(id_nao_existente));

        verify(monitoramentoRepository, times(1)).findById(id_nao_existente);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve salvar e retornar o novo TipoMonitoramento")
    void saveMonitoramento() {
        // Arrange
        TipoMonitoramento novoMonitoramento = new TipoMonitoramento();
        novoMonitoramento.setDescricao("Risco Baixo");
        novoMonitoramento.setFlagAtivo(true);

        // Mock do comportamento do save do repositório
        when(monitoramentoRepository.save(any(TipoMonitoramento.class))).thenReturn(monitoramento);

        // Act
        TipoMonitoramento resultado = tipoMonitoramentoService.save(novoMonitoramento);

        // Assert
        assertNotNull(resultado);

        // Verifica se o método save foi chamado com o objeto modificado
        verify(monitoramentoRepository, times(1)).save(novoMonitoramento);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve atualizar e retornar o TipoMonitoramento com os novos dados")
    void updateMonitoramento() {
        // Arrange
        TipoMonitoramento dadosNovos = new TipoMonitoramento();
        dadosNovos.setDescricao("Risco Médio Modificado");
        dadosNovos.setFlagAtivo(false);

        TipoMonitoramento monitoramentoOriginal = new TipoMonitoramento();
        monitoramentoOriginal.setIdTipoMonitoramento(id_existente);
        monitoramentoOriginal.setDescricao("Risco Alto");
        monitoramentoOriginal.setFlagAtivo(true);

        // Mock do findById necessário para o método update
        when(monitoramentoRepository.findById(id_existente)).thenReturn(Optional.of(monitoramentoOriginal));

        // Mock do save
        when(monitoramentoRepository.save(any(TipoMonitoramento.class))).thenReturn(monitoramentoOriginal);

        // Act
        TipoMonitoramento resultado = tipoMonitoramentoService.update(id_existente, dadosNovos);

        // Assert
        assertNotNull(resultado);

        // Verifica se os campos foram atualizados
        assertEquals("Risco Médio Modificado", resultado.getDescricao());
        assertFalse(resultado.getFlagAtivo());

        // Verifica se os métodos foram chamados
        verify(monitoramentoRepository, times(1)).findById(id_existente);
        verify(monitoramentoRepository, times(1)).save(monitoramentoOriginal);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve lançar EntityNotFoundException no update quando o id não for encontrado")
    void updateMonitoramentoNaoExiste() {
        // Arrange
        TipoMonitoramento dadosNovos = new TipoMonitoramento();
        when(monitoramentoRepository.findById(id_nao_existente)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class,
                () -> tipoMonitoramentoService.update(id_nao_existente, dadosNovos));

        verify(monitoramentoRepository, times(1)).findById(id_nao_existente);

        // Garante que o save não é chamado
        verify(monitoramentoRepository, never()).save(any());
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve deletar o TipoMonitoramento quando o id for encontrado")
    void deleteMonitoramento() {
        // Arrange
        // Mock do findById necessário para o método delete
        when(monitoramentoRepository.findById(id_existente)).thenReturn(Optional.of(monitoramento));
        doNothing().when(monitoramentoRepository).delete(monitoramento);

        // Act
        tipoMonitoramentoService.delete(id_existente);

        // Assert
        // Verifica se os métodos foram chamados na ordem e com os argumentos corretos
        verify(monitoramentoRepository, times(1)).findById(id_existente);
        verify(monitoramentoRepository, times(1)).delete(monitoramento);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve lançar EntityNotFoundException no delete quando o id não for encontrado")
    void deleteMonitoramentoNaoExiste() {
        // Arrange
        when(monitoramentoRepository.findById(id_nao_existente)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> tipoMonitoramentoService.delete(id_nao_existente));

        // Verifica se o findById foi chamado
        verify(monitoramentoRepository, times(1)).findById(id_nao_existente);

        // Verifica se o delete não é chamado
        verify(monitoramentoRepository, never()).delete(any());
    }
}
