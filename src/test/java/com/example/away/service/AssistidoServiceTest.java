package com.example.away.service;


import com.example.away.model.Assistido;
import com.example.away.repository.AssistidoRepository;
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
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Habilita o Mockito
public class AssistidoServiceTest {

    @Mock // Simula o comportamento do AssistidoRepository
    private AssistidoRepository assistidoRepository;

    @Mock
    private JdbcTemplate jdbcTemplate;

    // Mockito injetará mocks na classe que está sendo testada
    @InjectMocks
    private AssistidoService assistidoService;

    private Assistido assistidoMock;

    // SetUp antes de cada interação
    @BeforeEach
    void setUp() {
        // Objeto para usar nos testes
        assistidoMock = new Assistido();
        assistidoMock.setIdAssistido(1L);
        assistidoMock.setNumProcesso("12345");
    }

    @Test
    @DisplayName("TESTE UNITÁRIO > Deve retornar um Assistido quando o ID existir")
    void findById_QuandoEncontrado() {
        // Arrange (Configuração)
        when(assistidoRepository.findById(1L)).thenReturn(Optional.of(assistidoMock));

        // Act (Ação)
        Assistido resultado = assistidoService.findById(1L);

        // Assert (Verificação)
        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdAssistido());
        verify(assistidoRepository).findById(1L); // Verifica se o método do mock foi chamado
    }

    @Test
    @DisplayName("TESTE UNITÁRIO > Deve lançar EntityNotFoundException quando o ID não existir")
    void findById_QuandoNaoEncontrado() {
        // Arrange
        when(assistidoRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert (Ação e Verificação)
        assertThrows(EntityNotFoundException.class, () -> {
            assistidoService.findById(99L);
        });

        verify(assistidoRepository).findById(99L);
    }

    @Test
    @DisplayName("TESTE UNITÁRIO > Deve atualizar um Assistido existente")
    void update_ComSucesso() {
        // Arrange
        Date dataFixa = new Date();
        Assistido dadosParaAtualizar = new Assistido();
        dadosParaAtualizar.setNumProcesso("54321");

        // Mock para o findById que é chamado primeiro
        when(assistidoRepository.findById(1L)).thenReturn(Optional.of(assistidoMock));
        // Mock para o save
        when(assistidoRepository.save(any(Assistido.class))).thenReturn(assistidoMock);

        // Act
        assistidoService.update(1L, dadosParaAtualizar);

        // Assert
        ArgumentCaptor<Assistido> assistidoCaptor = ArgumentCaptor.forClass(Assistido.class);
        verify(assistidoRepository).save(assistidoCaptor.capture());

        Assistido assistidoAtualizado = assistidoCaptor.getValue();

        assertEquals(1L, assistidoAtualizado.getIdAssistido()); // ID não deve mudar
        assertEquals("54321", assistidoAtualizado.getNumProcesso());
        //assertEquals(dataFixa, assistidoAtualizado.getLastUpdateDate());
        assertEquals(1, assistidoAtualizado.getLastUpdatedBy());
    }

}
