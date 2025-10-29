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

import com.example.away.controller.TipoMonitoramentoController;
import com.example.away.model.TipoMonitoramento;
import com.example.away.service.TipoMonitoramentoService;

@ExtendWith(MockitoExtension.class)
@DisplayName("TESTE DE INTEGRAÇÃO - TipoMonitoramentoController")
class TipoMonitoramentoIntegrationTest {

    @Mock
    private TipoMonitoramentoService monitoramentoService;

    @InjectMocks
    private TipoMonitoramentoController monitoramentoController;

    private TipoMonitoramento tipoMonitoramento;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        tipoMonitoramento = new TipoMonitoramento();
        tipoMonitoramento.setIdTipoMonitoramento(1L);
        tipoMonitoramento.setDescricao("Monitoramento Eletrônico");
        tipoMonitoramento.setFlagAtivo(true);
        tipoMonitoramento.setCreatedBy(1);
        tipoMonitoramento.setCreationDate(new Date());
        tipoMonitoramento.setLastUpdatedBy(2);
        tipoMonitoramento.setLastUpdateDate(new Date());
    }

    // ---------- FIND ALL ----------
    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve retornar lista de TipoMonitoramento com sucesso")
    void deveRetornarListaDeMonitoramentos() {
        when(monitoramentoService.findAll()).thenReturn(List.of(tipoMonitoramento));

        ResponseEntity<List<TipoMonitoramento>> response = monitoramentoController.findAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Monitoramento Eletrônico", response.getBody().get(0).getDescricao());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve retornar BAD_REQUEST quando ocorrer exceção")
    void deveRetornarBadRequestNoFindAll() {
        when(monitoramentoService.findAll()).thenThrow(new RuntimeException("Erro ao buscar dados"));

        ResponseEntity<List<TipoMonitoramento>> response = monitoramentoController.findAll();

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // ---------- FIND BY ID ----------
    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve retornar TipoMonitoramento existente")
    void deveRetornarMonitoramentoPorId() {
        when(monitoramentoService.findById(1L)).thenReturn(tipoMonitoramento);

        ResponseEntity<TipoMonitoramento> response = monitoramentoController.findById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Monitoramento Eletrônico", response.getBody().getDescricao());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve retornar BAD_REQUEST ao ocorrer exceção")
    void deveRetornarBadRequestAoBuscarPorId() {
        when(monitoramentoService.findById(99L)).thenThrow(new RuntimeException("Erro"));

        ResponseEntity<TipoMonitoramento> response = monitoramentoController.findById(99L);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // ---------- SAVE ----------
    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve criar TipoMonitoramento com sucesso")
    void deveCriarMonitoramentoComSucesso() {
        when(monitoramentoService.save(any(TipoMonitoramento.class))).thenReturn(tipoMonitoramento);

        ResponseEntity<TipoMonitoramento> response = monitoramentoController.save(tipoMonitoramento);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Monitoramento Eletrônico", response.getBody().getDescricao());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve retornar BAD_REQUEST ao lançar exceção")
    void deveRetornarBadRequestAoSalvar() {
        when(monitoramentoService.save(any(TipoMonitoramento.class))).thenThrow(new RuntimeException("Erro"));

        ResponseEntity<TipoMonitoramento> response = monitoramentoController.save(tipoMonitoramento);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // ---------- UPDATE ----------
    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve atualizar TipoMonitoramento com sucesso")
    void deveAtualizarMonitoramentoComSucesso() {
        TipoMonitoramento atualizado = new TipoMonitoramento();
        atualizado.setIdTipoMonitoramento(1L);
        atualizado.setDescricao("Monitoramento Atualizado");
        atualizado.setFlagAtivo(false);

        when(monitoramentoService.update(eq(1L), any(TipoMonitoramento.class))).thenReturn(atualizado);

        ResponseEntity<TipoMonitoramento> response = monitoramentoController.update(1L, atualizado);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Monitoramento Atualizado", response.getBody().getDescricao());
        assertFalse(response.getBody().getFlagAtivo());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve retornar BAD_REQUEST ao lançar exceção")
    void deveRetornarBadRequestAoAtualizar() {
        when(monitoramentoService.update(eq(1L), any(TipoMonitoramento.class)))
                .thenThrow(new RuntimeException("Erro ao atualizar"));

        ResponseEntity<TipoMonitoramento> response = monitoramentoController.update(1L, tipoMonitoramento);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // ---------- DELETE ----------
    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve deletar TipoMonitoramento com sucesso")
    void deveDeletarMonitoramentoComSucesso() {
        doNothing().when(monitoramentoService).delete(1L);

        ResponseEntity<?> response = monitoramentoController.delete(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve retornar BAD_REQUEST ao lançar exceção")
    void deveRetornarBadRequestAoDeletar() {
        doThrow(new RuntimeException("Erro ao deletar")).when(monitoramentoService).delete(1L);

        ResponseEntity<?> response = monitoramentoController.delete(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
