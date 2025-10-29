package com.example.away.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

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

import com.example.away.controller.TipoRegimeController;
import com.example.away.model.TipoRegime;
import com.example.away.service.TipoRegimeService;

@ExtendWith(MockitoExtension.class)
@DisplayName("TESTE DE INTEGRAÇÃO - TipoRegimeController")
class TipoRegimeIntegrationTest {

    @Mock
    private TipoRegimeService regimeService;

    @InjectMocks
    private TipoRegimeController regimeController;

    private TipoRegime regime;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        regime = new TipoRegime();
        regime.setIdTipoRegime(1L);
        regime.setDescricao("Semiaberto");
        regime.setFlagAtivo(true);
        regime.setCreatedBy(1);
        regime.setCreationDate(new Date());
    }

    // ============================================================
    // ================== TESTES DE SUCESSO ========================
    // ============================================================

    @Test
    @DisplayName("Deve retornar lista de TipoRegime com sucesso - findAll")
    void deveRetornarListaDeTipoRegimeComSucesso() {
        when(regimeService.findAll()).thenReturn(List.of(regime));

        ResponseEntity<List<TipoRegime>> response = regimeController.findAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Semiaberto", response.getBody().get(0).getDescricao());
    }

    @Test
    @DisplayName("Deve retornar TipoRegime existente por ID com sucesso")
    void deveRetornarTipoRegimePorIdComSucesso() {
        when(regimeService.findById(1L)).thenReturn(regime);

        ResponseEntity<TipoRegime> response = regimeController.findById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Semiaberto", response.getBody().getDescricao());
        assertTrue(response.getBody().getFlagAtivo());
    }

    @Test
    @DisplayName("Deve salvar novo TipoRegime com sucesso")
    void deveSalvarTipoRegimeComSucesso() {
        when(regimeService.save(any(TipoRegime.class))).thenReturn(regime);

        ResponseEntity<TipoRegime> response = regimeController.save(regime);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Semiaberto", response.getBody().getDescricao());
    }

    @Test
    @DisplayName("Deve atualizar TipoRegime existente com sucesso")
    void deveAtualizarTipoRegimeComSucesso() {
        when(regimeService.update(eq(1L), any(TipoRegime.class))).thenReturn(regime);

        ResponseEntity<TipoRegime> response = regimeController.update(1L, regime);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Semiaberto", response.getBody().getDescricao());
    }

    @Test
    @DisplayName("Deve deletar TipoRegime com sucesso - retorno 204")
    void deveDeletarTipoRegimeComSucesso() {
        doNothing().when(regimeService).delete(1L);

        ResponseEntity<?> response = regimeController.delete(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(regimeService, times(1)).delete(1L);
    }

    // ============================================================
    // =================== TESTES DE ERRO =========================
    // ============================================================

    @Test
    @DisplayName("findAll deve retornar BAD_REQUEST em caso de exceção")
    void deveRetornarBadRequestNoFindAllQuandoOcorreErro() {
        when(regimeService.findAll()).thenThrow(new RuntimeException("Erro no banco"));

        ResponseEntity<List<TipoRegime>> response = regimeController.findAll();

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("findById deve retornar BAD_REQUEST quando ocorre erro")
    void deveRetornarBadRequestNoFindByIdQuandoOcorreErro() {
        when(regimeService.findById(anyLong())).thenThrow(new RuntimeException("Erro inesperado"));

        ResponseEntity<TipoRegime> response = regimeController.findById(999L);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("save deve retornar BAD_REQUEST quando ocorre erro")
    void deveRetornarBadRequestNoSaveQuandoOcorreErro() {
        when(regimeService.save(any(TipoRegime.class))).thenThrow(new RuntimeException("Erro ao salvar"));

        ResponseEntity<TipoRegime> response = regimeController.save(regime);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("update deve retornar BAD_REQUEST quando ocorre erro")
    void deveRetornarBadRequestNoUpdateQuandoOcorreErro() {
        when(regimeService.update(anyLong(), any(TipoRegime.class)))
            .thenThrow(new RuntimeException("Erro ao atualizar"));

        ResponseEntity<TipoRegime> response = regimeController.update(1L, regime);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("delete deve retornar BAD_REQUEST quando ocorre erro")
    void deveRetornarBadRequestNoDeleteQuandoOcorreErro() {
        doThrow(new RuntimeException("Erro ao deletar")).when(regimeService).delete(1L);

        ResponseEntity<?> response = regimeController.delete(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
