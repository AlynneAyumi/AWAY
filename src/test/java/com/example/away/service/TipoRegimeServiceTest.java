package com.example.away.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.away.model.TipoRegime;
import com.example.away.repository.TipoRegimeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class TipoRegimeServiceTest {

    // Injeta a classe a ser testada, e injeta os Mocks marcados com @Mock
    @InjectMocks
    private TipoRegimeService tipoRegimeService;

    // Cria um mock para a dependência TipoRegimeRepository
    @Mock
    private TipoRegimeRepository regimeRepository;

    private TipoRegime regime;
    private final Long id_existente = 5L;
    private final Long id_nao_existente = 99L;

    // Método de configuração que é executado antes de cada teste
    @BeforeEach
    void setUp() {
        // Inicializa um objeto TipoRegime para usar nos testes
        regime = new TipoRegime();
        regime.setIdTipoRegime(id_existente);
        regime.setDescricao("Regime Semi-Aberto");
        regime.setFlagAtivo(true);
    }

    // Cenários
    @Test
    @DisplayName("TESTE UNITÁRIO > Deve retornar uma lista de TipoRegime")
    void findAllListaDeRegimes() {
        // Arrange
        TipoRegime r2 = new TipoRegime();
        r2.setIdTipoRegime(6L);
        List<TipoRegime> listaEsperada = Arrays.asList(regime, r2);

        // Simula o comportamento do repositório
        when(regimeRepository.findAll()).thenReturn(listaEsperada);

        // Act
        List<TipoRegime> listaAtual = tipoRegimeService.findAll();

        // Assert
        assertNotNull(listaAtual);
        assertEquals(2, listaAtual.size());
        assertEquals(listaEsperada, listaAtual);

        // Verifica se o método do repositório foi chamado pelo menos uma vez
        verify(regimeRepository, times(1)).findAll();
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve retornar TipoRegime quando o id for encontrado")
    void findByIdRegimeExiste() {
        // Arrange
        when(regimeRepository.findById(id_existente)).thenReturn(Optional.of(regime));

        // Act
        TipoRegime resultado = tipoRegimeService.findById(id_existente);

        // Assert
        assertNotNull(resultado);
        assertEquals(id_existente, resultado.getIdTipoRegime());

        // Verifica se o método foi chamado pelo menos uma vez
        verify(regimeRepository, times(1)).findById(id_existente);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve lançar EntityNotFoundException quando o id não for encontrado")
    void findByIdRegimeNaoExiste() {
        // Arrange
        when(regimeRepository.findById(id_nao_existente)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> tipoRegimeService.findById(id_nao_existente));

        verify(regimeRepository, times(1)).findById(id_nao_existente);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve salvar e retornar o novo TipoRegime")
    void saveRegime() {
        // Arrange
        TipoRegime novoRegime = new TipoRegime();
        novoRegime.setDescricao("Regime Aberto");
        novoRegime.setFlagAtivo(true);

        // Mock do comportamento do save do repositório
        when(regimeRepository.save(any(TipoRegime.class))).thenReturn(novoRegime);

        // Act
        TipoRegime resultado = tipoRegimeService.save(novoRegime);

        // Assert
        assertNotNull(resultado);

        // Verifica se o método save foi chamado pelo menos uma vez
        verify(regimeRepository, times(1)).save(novoRegime);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve atualizar e retornar o TipoRegime com os novos dados")
    void updateRegime() {
        // Arrange
        TipoRegime dadosNovos = new TipoRegime();
        dadosNovos.setDescricao("Regime Fechado Modificado");
        dadosNovos.setFlagAtivo(false);

        TipoRegime regimeOriginal = new TipoRegime();
        regimeOriginal.setIdTipoRegime(id_existente);
        regimeOriginal.setDescricao("Regime Fechado");
        regimeOriginal.setFlagAtivo(true);

        // Mock do findById
        when(regimeRepository.findById(id_existente)).thenReturn(Optional.of(regimeOriginal));

        // Mock do save
        when(regimeRepository.save(any(TipoRegime.class))).thenReturn(regimeOriginal);

        // Act
        TipoRegime resultado = tipoRegimeService.update(id_existente, dadosNovos);

        // Assert
        assertNotNull(resultado);

        // Verifica se os campos foram atualizados
        assertEquals("Regime Fechado Modificado", resultado.getDescricao());
        assertFalse(resultado.getFlagAtivo());

        // Verifica se os métodos foram chamados na ordem pelo menos uma vez
        verify(regimeRepository, times(1)).findById(id_existente);
        verify(regimeRepository, times(1)).save(regimeOriginal);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve lançar EntityNotFoundException quando o id não for encontrado")
    void updateRegimeNaoExiste() {
        // Arrange
        TipoRegime dadosNovos = new TipoRegime();
        when(regimeRepository.findById(id_nao_existente)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class,
                () -> tipoRegimeService.update(id_nao_existente, dadosNovos));

        verify(regimeRepository, times(1)).findById(id_nao_existente);

        // Garante que o método save não é chamado
        verify(regimeRepository, never()).save(any());
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve deletar o TipoRegime quando o id for encontrado")
    void deleteRegime() {
        // Arrange
        // Mock do findById necessário para o método delete
        when(regimeRepository.findById(id_existente)).thenReturn(Optional.of(regime));

        // O método delete do repositório é void, então apenas verifica.
        doNothing().when(regimeRepository).delete(regime);

        // Act
        tipoRegimeService.delete(id_existente);

        // Assert
        // Verifica se os métodos foram chamados na ordem e com os argumentos corretos
        verify(regimeRepository, times(1)).findById(id_existente);
        verify(regimeRepository, times(1)).delete(regime);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve lançar EntityNotFoundException no delete quando o id não for encontrado")
    void deleteRegimeNaoExiste() {
        // Arrange
        when(regimeRepository.findById(id_nao_existente)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> tipoRegimeService.delete(id_nao_existente));

        // Garante que o delete não é chamado nenhuma vez
        verify(regimeRepository, never()).delete(any());
    }
}