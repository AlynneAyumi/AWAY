package com.example.away.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

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

import com.example.away.controller.ComparecimentoController;
import com.example.away.exception.ResourceNotFoundException;
import com.example.away.model.Assistido;
import com.example.away.model.Comparecimento;
import com.example.away.service.ComparecimentoService;

@ExtendWith(MockitoExtension.class)
@DisplayName("TESTE DE INTEGRAÇÃO - ComparecimentoController")
class ComparecimentoIntegrationTest {

    @Mock
    private ComparecimentoService comparecimentoService;

    @InjectMocks
    private ComparecimentoController comparecimentoController;

    private Comparecimento comparecimento;
    private Assistido assistido;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        assistido = new Assistido();
        assistido.setIdAssistido(1L);

        comparecimento = new Comparecimento();
        comparecimento.setIdComparecimento(1L);
        comparecimento.setData(new Date());
        comparecimento.setFlagComparecimento(true);
        comparecimento.setObservacoes("Observação teste");
        comparecimento.setAssistido(assistido);
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve salvar Comparecimento com sucesso")
    void deveSalvarComparecimentoComSucesso() {
        when(comparecimentoService.save(any(Comparecimento.class))).thenReturn(comparecimento);

        ResponseEntity<Comparecimento> response = comparecimentoController.save(comparecimento);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(comparecimento.getIdComparecimento(), response.getBody().getIdComparecimento());
        assertEquals(true, response.getBody().getFlagComparecimento());
        assertEquals(assistido.getIdAssistido(), response.getBody().getAssistido().getIdAssistido());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve retornar BAD_REQUEST ao salvar Comparecimento nulo")
    void deveRetornarBadRequestAoSalvarNulo() {
        ResponseEntity<Comparecimento> response = comparecimentoController.save(null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - FindAll deve retornar lista de Comparecimentos")
    void deveRetornarListaComparecimentosNoFindAll() {
        when(comparecimentoService.findAll()).thenReturn(List.of(comparecimento));

        ResponseEntity<List<Comparecimento>> response = comparecimentoController.findAll();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(comparecimento.getIdComparecimento(), response.getBody().get(0).getIdComparecimento());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - FindById deve retornar Comparecimento existente")
    void deveRetornarComparecimentoExistenteNoFindById() {
        Long id = 1L;
        when(comparecimentoService.findById(id)).thenReturn(comparecimento);

        ResponseEntity<Comparecimento> response = comparecimentoController.findById(id);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(comparecimento.getIdComparecimento(), response.getBody().getIdComparecimento());
    }

    @Test
    @DisplayName("Deve retornar NOT_FOUND para Comparecimento inexistente")
    void deveRetornarNotFoundAoBuscarComparecimentoInexistente() {
        Long id = 999L;
        when(comparecimentoService.findById(999L))
            .thenThrow(new ResourceNotFoundException("TipoComparecimento não encontrado"));

        ResponseEntity<?> response = comparecimentoController.findById(id);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve atualizar Comparecimento existente com sucesso")
    void deveAtualizarComparecimentoExistenteComSucesso() {
        Long id = 1L;
        when(comparecimentoService.update(id, comparecimento)).thenReturn(comparecimento);

        ResponseEntity<Comparecimento> response = comparecimentoController.update(id, comparecimento);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(comparecimento.getIdComparecimento(), response.getBody().getIdComparecimento());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Deve deletar Comparecimento existente com sucesso")
    void deveDeletarComparecimentoComSucesso() {
        Long id = 1L;
        doNothing().when(comparecimentoService).delete(id);

        ResponseEntity<?> response = comparecimentoController.delete(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Buscar Comparecimento por data")
    void deveBuscarComparecimentoPorData() {
        Date data = new Date();
        when(comparecimentoService.buscarPorDataComparecimento(data)).thenReturn(List.of(comparecimento));

        ResponseEntity<List<Comparecimento>> response = comparecimentoController.buscarPorDataComparecimento(data);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(comparecimento.getIdComparecimento(), response.getBody().get(0).getIdComparecimento());
    }

    @Test
    @DisplayName("TESTE DE INTEGRAÇÃO - Buscar Comparecimento por flagComparecimento")
    void deveBuscarComparecimentoPorFlag() {
        Boolean flag = true;
        when(comparecimentoService.buscarPorFlagComparecimento(flag)).thenReturn(List.of(comparecimento));

        ResponseEntity<List<Comparecimento>> response = comparecimentoController.buscarPorFlagComparecimento(flag);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(true, response.getBody().get(0).getFlagComparecimento());
    }
}
