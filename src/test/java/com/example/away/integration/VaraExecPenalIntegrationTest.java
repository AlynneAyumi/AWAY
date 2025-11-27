package com.example.away.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

import com.example.away.controller.VaraExecPenalController;
import com.example.away.model.VaraExecPenal;
import com.example.away.service.VaraExecPenalService;

@ExtendWith(MockitoExtension.class)
@DisplayName("TESTE DE INTEGRAÇÃO - VaraExecPenalController")
class VaraExecPenalIntegrationTest {

    @Mock
    private VaraExecPenalService varaExecPenalService;

    @InjectMocks
    private VaraExecPenalController varaExecPenalController;

    private VaraExecPenal vara;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        vara = new VaraExecPenal();
        vara.setIdVaraExecPenal(1L);
        vara.setNome("Vara Criminal de Cascavel");
        vara.setDescricao("Responsável pela execução penal da comarca");
        vara.setCreationDate(new Date());
        vara.setCreatedBy(1);
    }

    // ---------------------------------------------------------
    // CREATE
    // ---------------------------------------------------------
    @Test
    @DisplayName("Cenário de sucesso ao salvar VaraExecPenal")
    void deveSalvarVaraExecPenalComSucesso() {
        when(varaExecPenalService.save(any(VaraExecPenal.class))).thenReturn(vara);

        ResponseEntity<VaraExecPenal> response = varaExecPenalController.save(vara);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Vara Criminal de Cascavel", response.getBody().getNome());
    }

    @Test
    @DisplayName("Deve retornar BAD_REQUEST ao tentar salvar VaraExecPenal com erro")
    void deveRetornarBadRequestAoSalvarComErro() {
        when(varaExecPenalService.save(any(VaraExecPenal.class))).thenThrow(new RuntimeException("Erro ao salvar"));

        ResponseEntity<VaraExecPenal> response = varaExecPenalController.save(vara);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // ---------------------------------------------------------
    // FIND ALL
    // ---------------------------------------------------------
    @Test
    @DisplayName("FindAll deve retornar lista de VaraExecPenal")
    void deveRetornarListaDeVaraExecPenal() {
        when(varaExecPenalService.findAll()).thenReturn(List.of(vara));

        ResponseEntity<List<VaraExecPenal>> response = varaExecPenalController.findAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Vara Criminal de Cascavel", response.getBody().get(0).getNome());
    }

    @Test
    @DisplayName("FindAll deve retornar BAD_REQUEST ao lançar exceção")
    void deveRetornarBadRequestNoFindAll() {
        when(varaExecPenalService.findAll()).thenThrow(new RuntimeException("Erro ao buscar"));

        ResponseEntity<List<VaraExecPenal>> response = varaExecPenalController.findAll();

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // ---------------------------------------------------------
    // FIND BY ID
    // ---------------------------------------------------------
    @Test
    @DisplayName("FindById deve retornar VaraExecPenal existente")
    void deveRetornarVaraExecPenalExistente() {
        when(varaExecPenalService.findById(1L)).thenReturn(vara);

        ResponseEntity<VaraExecPenal> response = varaExecPenalController.findById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Vara Criminal de Cascavel", response.getBody().getNome());
    }

    @Test
    @DisplayName("FindById deve retornar BAD_REQUEST em caso de erro")
    void deveRetornarBadRequestNoFindById() {
        when(varaExecPenalService.findById(1L)).thenThrow(new RuntimeException("Erro ao buscar"));

        ResponseEntity<VaraExecPenal> response = varaExecPenalController.findById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // ---------------------------------------------------------
    // UPDATE
    // ---------------------------------------------------------
    @Test
    @DisplayName("Update deve atualizar VaraExecPenal com sucesso")
    void deveAtualizarVaraExecPenalComSucesso() {
        VaraExecPenal atualizada = new VaraExecPenal();
        atualizada.setIdVaraExecPenal(1L);
        atualizada.setNome("Vara Criminal Atualizada");
        atualizada.setDescricao("Descrição atualizada");

        when(varaExecPenalService.update(eq(1L), any(VaraExecPenal.class))).thenReturn(atualizada);

        ResponseEntity<VaraExecPenal> response = varaExecPenalController.update(1L, atualizada);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Vara Criminal Atualizada", response.getBody().getNome());
    }

    @Test
    @DisplayName("Update deve retornar BAD_REQUEST ao ocorrer erro")
    void deveRetornarBadRequestAoAtualizarComErro() {
        when(varaExecPenalService.update(eq(1L), any(VaraExecPenal.class))).thenThrow(new RuntimeException("Erro no update"));

        ResponseEntity<VaraExecPenal> response = varaExecPenalController.update(1L, vara);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // ---------------------------------------------------------
    // DELETE
    // ---------------------------------------------------------
    @Test
    @DisplayName("Delete deve excluir VaraExecPenal com sucesso")
    void deveExcluirVaraExecPenalComSucesso() {
        doNothing().when(varaExecPenalService).delete(1L);

        ResponseEntity<?> response = varaExecPenalController.delete(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("Delete deve retornar BAD_REQUEST ao lançar exceção")
    void deveRetornarBadRequestAoExcluirComErro() {
        doThrow(new RuntimeException("Erro ao deletar")).when(varaExecPenalService).delete(1L);

        ResponseEntity<?> response = varaExecPenalController.delete(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
