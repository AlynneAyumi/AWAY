package com.example.away.service;

import com.example.away.model.Endereco;
import com.example.away.model.Pessoa;
import com.example.away.model.Usuario;
import com.example.away.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// @ExtendWith(MockitoExtension.class) para inicializar os Mocks
@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    // Simula a dependência (Repository)
    @Mock
    private UsuarioRepository usuarioRepository;

    // Injeta automaticamente os @Mock's no construtor.
    @InjectMocks
    private UsuarioService usuarioService;

    // Objeto base para reuso nos testes
    private Usuario usuarioPadrao;
    private Date dataAtual;

    @BeforeEach
    void setUp() {

        dataAtual = new Date();

        // Inicializa um objeto Usuario padrão para facilitar a criação de cenários de teste
        usuarioPadrao = new Usuario();
        usuarioPadrao.setIdUsuario(1L);
        usuarioPadrao.setEmail("teste@email.com");
        usuarioPadrao.setNomeUser("user_teste");
        usuarioPadrao.setNome("Nome Teste");
        usuarioPadrao.setSenha("12345");
        usuarioPadrao.setPerfil("AGENTE");
        usuarioPadrao.setAtivo(true);
    }


    // Cenários
    @Test
    @DisplayName("TESTE UNITÁRIO > Deve retornar todos os Usuarios")
    void findAllUsuarios() {
        // Cenário: O repositório retorna uma lista com o usuário padrão
        List<Usuario> listaEsperada = Arrays.asList(usuarioPadrao);
        when(usuarioRepository.findAll()).thenReturn(listaEsperada);

        // Ação: Chama o metodo a ser testado
        List<Usuario> resultado = usuarioService.findAll();

        // Verificação:
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(usuarioPadrao.getNomeUser(), resultado.get(0).getNomeUser());

        // Verifica se o metodo do repositorio foi chamado exatamente uma vez
        verify(usuarioRepository, times(1)).findAll();
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve buscar Usuario por ID")
    void findByIdUsuario() {
        // Cenário: O repositório encontra o usuário
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioPadrao));

        // Ação
        Usuario resultado = usuarioService.findById(1L);

        // Verificação
        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdUsuario());
        verify(usuarioRepository, times(1)).findById(1L);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve retornar exceção ao buscar Usuario por ID")
    void findByIdUsuarioInexistente() {
        // Cenário: O repositório não encontra o usuário (Optional.empty())
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        // Ação e Verificação: Deve lançar EntityNotFoundException
        assertThrows(EntityNotFoundException.class, () -> {
            usuarioService.findById(99L);
        });

        verify(usuarioRepository, times(1)).findById(99L);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve salvar registro Usuario")
    void saveUsuario() {

        // Cenário: Cria um novo usuário (sem ID, Perfil e Ativo)
        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail("novo@email.com");
        novoUsuario.setNomeUser("novo_user");
        novoUsuario.setNome("Novo Nome");
        novoUsuario.setSenha("nova123");

        // Comportamento do Repositório
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioPadrao);

        // Ação
        Usuario resultado = usuarioService.save(novoUsuario);

        // Verificação:
        // 1. Campo de Auditoria
        assertEquals(1, novoUsuario.getCreatedBy());

        // 2. Valores Padrão
        assertEquals("AGENTE", novoUsuario.getPerfil());
        assertTrue(novoUsuario.getAtivo());

        // 3. Verifica se acessou o repositório pelo menos uma vez
        verify(usuarioRepository, times(1)).save(novoUsuario);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve salvar Usuario com associação de Pessoa e Endereço")
    void savePessoaEndereco() {

        // Cenário: Cria Pessoa e Endereco
        Endereco endereco = new Endereco();
        Pessoa pessoa = new Pessoa();
        pessoa.setEndereco(endereco);

        Usuario novoUsuario = new Usuario();
        novoUsuario.setPessoa(pessoa); // Adiciona a Pessoa

        // Comportamento do Repositório
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioPadrao);

        // Ação
        usuarioService.save(novoUsuario);

        // Verificação:
        // Auditoria do Usuário
        assertEquals(1, novoUsuario.getCreatedBy());
        // Auditoria da Pessoa
        assertEquals(1, novoUsuario.getPessoa().getCreatedBy());
        // Auditoria do Endereço
        assertEquals(1, novoUsuario.getPessoa().getEndereco().getCreatedBy());
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve atualizar registro Usuario")
    void updateUsuario() {

        // Cenário Inicial: O usuário existente no banco
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setIdUsuario(1L);
        usuarioExistente.setEmail("antigo@email.com");
        usuarioExistente.setNome("Antigo Nome");

        // Cenário de Update: O objeto com os novos dados
        Usuario usuarioNovosDados = new Usuario();
        usuarioNovosDados.setEmail("novo@email.com");
        usuarioNovosDados.setNome("Novo Nome");

        // 1. Mockar findById: Retorna o usuário existente
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioExistente));

        // 2. Mockar save: Retorna o usuário atualizado (após a modificação)
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioExistente);

        // Ação
        Usuario resultado = usuarioService.update(1L, usuarioNovosDados);

        // Verificação:
        // 1. O objeto retornado é o atualizado
        assertEquals("novo@email.com", resultado.getEmail());
        assertEquals("Novo Nome", resultado.getNome());

        // 2. Os métodos corretos foram chamados
        verify(usuarioRepository, times(1)).findById(1L);
        verify(usuarioRepository, times(1)).save(usuarioExistente); // verifica que salvou o objeto

    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve cair em exceção ao tentar atualizar Usuario com ID inexistente")
    void updateUsuarioInexistente() {

        // Cenário: findById lança a exceção (mockado no início da execução do update)
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        // Ação & Verificação:
        assertThrows(EntityNotFoundException.class, () -> {
            usuarioService.update(99L, new Usuario());
        });

        // Garante que o metodo save não foi chamado
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve apagar registro Usuario")
    void deleteUsuario() {

        // Cenário: findById encontra o usuário
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioPadrao));

        // Ação
        usuarioService.delete(1L);

        // Verificação:
        verify(usuarioRepository, times(1)).findById(1L);
        // Verifica se o metodo de deleção do repositório foi chamado com o objeto encontrado
        verify(usuarioRepository, times(1)).delete(usuarioPadrao);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve cair em exceção ao tentar excluir Usuario inexistente")
    void deleteUsuarioInexistente() {

        // Cenário: findById não encontra o usuário
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        // Ação & Verificação:
        assertThrows(EntityNotFoundException.class, () -> {
            usuarioService.delete(99L);
        });

        // Garante que o metodo de deleção não foi chamado
        verify(usuarioRepository, never()).delete(any(Usuario.class));
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve buscar Usuario através do email")
    void findByEmailUsuario() {

        // Cenário
        String email = "teste@email.com";
        when(usuarioRepository.findByEmail(email)).thenReturn(usuarioPadrao);

        // Ação
        Usuario resultado = usuarioService.findByEmail(email);

        // Verificação
        assertNotNull(resultado);
        assertEquals(email, resultado.getEmail());
        verify(usuarioRepository, times(1)).findByEmail(email);
    }


    @Test
    @DisplayName("TESTE UNITÁRIO > Deve retornar vazio ao tentar buscar Usuario com email inexistente")
    void findByEmailUsuarioInexistente() {
        // Cenário
        String email = "naoexiste@email.com";
        when(usuarioRepository.findByEmail(email)).thenReturn(null);

        // Ação
        Usuario resultado = usuarioService.findByEmail(email);

        // Verificação
        assertNull(resultado);
        verify(usuarioRepository, times(1)).findByEmail(email);
    }
    
}