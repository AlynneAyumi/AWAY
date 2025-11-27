package com.example.away.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import com.example.away.model.TipoSituacao;
import com.example.away.repository.TipoSituacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class TipoSituacaoServiceTest {

    // Injeta a classe a ser testada, e injeta os Mocks marcados com @Mock
    @InjectMocks
    private TipoSituacaoService tipoSituacaoService;

    // Cria um mock para a dependência TipoSituacaoRepository
    @Mock
    private TipoSituacaoRepository situacaoRepository;

    private TipoSituacao situacao;
    private final Long id_existente = 10L;
    private final Long id_nao_existente = 99L;

    // Método de configuração que é executado antes de cada teste
    @BeforeEach
    void setUp() {
        // Inicializa um objeto TipoSituacao para usar nos testes
        situacao = new TipoSituacao();
        situacao.setIdTipoSituacao(id_existente);
        situacao.setDescricao("Ativo");
        situacao.setFlagAtivo(true);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve retornar uma lista de TipoSituacao")
    void findAllListaDeSituacoes() {
        // Arrange
        TipoSituacao s2 = new TipoSituacao();
        s2.setIdTipoSituacao(11L);
        List<TipoSituacao> listaEsperada = Arrays.asList(situacao, s2);

        // Simula o comportamento do repositório
        when(situacaoRepository.findAll()).thenReturn(listaEsperada);

        // Act
        List<TipoSituacao> listaAtual = tipoSituacaoService.findAll();

        // Assert
        assertNotNull(listaAtual);
        assertEquals(2, listaAtual.size());
        assertEquals(listaEsperada, listaAtual);

        // Verifica se o método do repositório foi chamado pelo menos uma vez
        verify(situacaoRepository, times(1)).findAll();
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve retornar TipoSituacao quando o id for encontrado")
    void findByIdSituacaoExiste() {
        // Arrange
        when(situacaoRepository.findById(id_existente)).thenReturn(Optional.of(situacao));

        // Act
        TipoSituacao resultado = tipoSituacaoService.findById(id_existente);

        // Assert
        assertNotNull(resultado);
        assertEquals(id_existente, resultado.getIdTipoSituacao());

        verify(situacaoRepository, times(1)).findById(id_existente);
    }

    @Test
    @DisplayName("TESTE UNITÁRIO > Deve lançar EntityNotFoundException quando o id não for encontrado")
    void findByIdSituacaoNaoExiste() {
        // Arrange
        when(situacaoRepository.findById(id_nao_existente)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> tipoSituacaoService.findById(id_nao_existente));

        verify(situacaoRepository, times(1)).findById(id_nao_existente);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve salvar e retornar o novo TipoSituacao")
    void saveSituacao() {
        // Arrange
        TipoSituacao novaSituacao = new TipoSituacao();
        novaSituacao.setDescricao("Pendente");
        novaSituacao.setFlagAtivo(true);

        // Mock do comportamento do save do repositório
        when(situacaoRepository.save(any(TipoSituacao.class))).thenReturn(novaSituacao);

        // Act
        TipoSituacao resultado = tipoSituacaoService.save(novaSituacao);

        // Assert
        assertNotNull(resultado);

        // Verifica se o método save foi chamado pelo menos uma vez
        verify(situacaoRepository, times(1)).save(novaSituacao);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve atualizar e retornar o TipoSituacao com os novos dados")
    void updateSituacao() {
        // Arrange
        TipoSituacao dadosNovos = new TipoSituacao();
        dadosNovos.setDescricao("Inativo Modificado");
        dadosNovos.setFlagAtivo(false);

        TipoSituacao situacaoOriginal = new TipoSituacao();
        situacaoOriginal.setIdTipoSituacao(id_existente);
        situacaoOriginal.setDescricao("Inativo");
        situacaoOriginal.setFlagAtivo(true);

        // Mock do findById
        when(situacaoRepository.findById(id_existente)).thenReturn(Optional.of(situacaoOriginal));

        // Mock do save
        when(situacaoRepository.save(any(TipoSituacao.class))).thenReturn(situacaoOriginal);

        // Act
        TipoSituacao resultado = tipoSituacaoService.update(id_existente, dadosNovos);

        // Assert
        assertNotNull(resultado);

        // Verifica se os campos de descrição e flagAtivo foram atualizados
        assertEquals("Inativo Modificado", resultado.getDescricao());
        assertFalse(resultado.getFlagAtivo());

        // Verifica se os métodos foram chamados
        verify(situacaoRepository, times(1)).findById(id_existente);
        verify(situacaoRepository, times(1)).save(situacaoOriginal);
    }

    @Test
    @DisplayName("TESTE UNITÁRIO > Deve lançar EntityNotFoundException quando o id não for encontrado")
    void updateSituacaoNaoExiste() {
        // Arrange
        TipoSituacao dadosNovos = new TipoSituacao();
        when(situacaoRepository.findById(id_nao_existente)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class,
                () -> tipoSituacaoService.update(id_nao_existente, dadosNovos));

        verify(situacaoRepository, times(1)).findById(id_nao_existente);

        // Garante que o save não é chamado nenhuma vez
        verify(situacaoRepository, never()).save(any());
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve deletar o TipoSituacao quando o id for encontrado")
    void deleteSituacao() {
        // Arrange
        // Mock do findById
        when(situacaoRepository.findById(id_existente)).thenReturn(Optional.of(situacao));

        // O método delete do repositório é void
        doNothing().when(situacaoRepository).delete(situacao);

        // Act
        tipoSituacaoService.delete(id_existente);

        // Assert
        verify(situacaoRepository, times(1)).findById(id_existente);
        verify(situacaoRepository, times(1)).delete(situacao);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve lançar EntityNotFoundException no delete quando o id não for encontrado")
    void deleteSituacaoNaoExiste() {
        // Arrange
        when(situacaoRepository.findById(id_nao_existente)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> tipoSituacaoService.delete(id_nao_existente));

        // Garante que o delete não é chamado
        verify(situacaoRepository, never()).delete(any());
    }
}