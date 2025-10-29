package com.example.away.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.List;

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

import com.example.away.controller.TipoSituacaoController;
import com.example.away.model.TipoSituacao;
import com.example.away.service.TipoSituacaoService;

@ExtendWith(MockitoExtension.class)
@DisplayName("TESTE DE INTEGRAÇÃO - TipoSituacaoController")
class TipoSituacaoIntegrationTest {

    @Mock
    private TipoSituacaoService situacaoService;

    @InjectMocks
    private TipoSituacaoController situacaoController;

    private TipoSituacao tipoSituacao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        tipoSituacao = new TipoSituacao();
        tipoSituacao.setIdTipoSituacao(1L);
        tipoSituacao.setDescricao("Em andamento");
        tipoSituacao.setFlagAtivo(true);
        tipoSituacao.setCreatedBy(1);
        tipoSituacao.setCreationDate(new Date());
        tipoSituacao.setLastUpdatedBy(1);
        tipoSituacao.setLastUpdateDate(new Date());
    }

    @Test
    @DisplayName("Cenário de sucesso ao salvar TipoSituacao")
    void deveSalvarTipoSituacaoComSucesso() {
        when(situacaoService.save(any(TipoSituacao.class))).thenReturn(tipoSituacao);

        ResponseEntity<TipoSituacao> response = situacaoController.save(tipoSituacao);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Em andamento", response.getBody().getDescricao());
        verify(situacaoService, times(1)).save(any(TipoSituacao.class));
    }

    @Test
    @DisplayName("Deve retornar BAD_REQUEST ao salvar TipoSituacao com erro no serviço")
    void deveRetornarBadRequestAoSalvarComErro() {
        when(situacaoService.save(any(TipoSituacao.class))).thenThrow(new RuntimeException("Erro interno"));

        ResponseEntity<TipoSituacao> response = situacaoController.save(tipoSituacao);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Cenário de sucesso ao buscar todas as situações")
    void deveRetornarListaDeTipoSituacaoComSucesso() {
        when(situacaoService.findAll()).thenReturn(List.of(tipoSituacao));

        ResponseEntity<List<TipoSituacao>> response = situacaoController.findAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Em andamento", response.getBody().get(0).getDescricao());
        verify(situacaoService, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar BAD_REQUEST ao ocorrer erro em findAll")
    void deveRetornarBadRequestEmFindAllComErro() {
        when(situacaoService.findAll()).thenThrow(new RuntimeException("Erro inesperado"));

        ResponseEntity<List<TipoSituacao>> response = situacaoController.findAll();

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Cenário de sucesso ao buscar TipoSituacao por ID")
    void deveRetornarTipoSituacaoPorIdComSucesso() {
        Long id = 1L;
        when(situacaoService.findById(id)).thenReturn(tipoSituacao);

        ResponseEntity<TipoSituacao> response = situacaoController.findById(id);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Em andamento", response.getBody().getDescricao());
        verify(situacaoService, times(1)).findById(id);
    }

    @Test
    @DisplayName("Deve retornar BAD_REQUEST ao buscar TipoSituacao inexistente")
    void deveRetornarBadRequestAoBuscarInexistente() {
        Long id = 999L;
        when(situacaoService.findById(id)).thenThrow(new RuntimeException("Não encontrado"));

        ResponseEntity<TipoSituacao> response = situacaoController.findById(id);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Cenário de sucesso ao atualizar TipoSituacao existente")
    void deveAtualizarTipoSituacaoComSucesso() {
        Long id = 1L;
        tipoSituacao.setDescricao("Concluído");

        when(situacaoService.update(eq(id), any(TipoSituacao.class))).thenReturn(tipoSituacao);

        ResponseEntity<TipoSituacao> response = situacaoController.update(id, tipoSituacao);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Concluído", response.getBody().getDescricao());
        verify(situacaoService, times(1)).update(eq(id), any(TipoSituacao.class));
    }

    @Test
    @DisplayName("Deve retornar BAD_REQUEST ao atualizar TipoSituacao inexistente")
    void deveRetornarBadRequestAoAtualizarInexistente() {
        Long id = 999L;
        when(situacaoService.update(eq(id), any(TipoSituacao.class))).thenThrow(new RuntimeException("Erro ao atualizar"));

        ResponseEntity<TipoSituacao> response = situacaoController.update(id, tipoSituacao);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Cenário de sucesso ao deletar TipoSituacao")
    void deveDeletarTipoSituacaoComSucesso() {
        Long id = 1L;
        doNothing().when(situacaoService).delete(id);

        ResponseEntity<?> response = situacaoController.delete(id);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(situacaoService, times(1)).delete(id);
    }

    @Test
    @DisplayName("Deve retornar BAD_REQUEST ao ocorrer erro na exclusão")
    void deveRetornarBadRequestAoFalharNaExclusao() {
        Long id = 999L;
        doThrow(new RuntimeException("Erro ao deletar")).when(situacaoService).delete(id);

        ResponseEntity<?> response = situacaoController.delete(id);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(situacaoService, times(1)).delete(id);
    }
}
