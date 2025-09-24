package com.example.away.service;

import java.sql.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import com.example.away.model.Usuario;
import com.example.away.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public Usuario save(Usuario usuario) {
        Date hoje = UtilService.getDataAtual();

        usuario.setCreatedBy(1);
        usuario.setCreationDate(hoje);
        
        // Definir valores padrão se não fornecidos
        if (usuario.getPerfil() == null) {
            usuario.setPerfil("AGENTE");
        }
        if (usuario.getAtivo() == null) {
            usuario.setAtivo(true);
        }

        // Configurar pessoa se existir
        if (usuario.getPessoa() != null) {
            usuario.getPessoa().setCreatedBy(1);
            usuario.getPessoa().setCreationDate(hoje);
            
            // Configurar endereço se existir
            if (usuario.getPessoa().getEndereco() != null) {
                usuario.getPessoa().getEndereco().setCreatedBy(1);
                usuario.getPessoa().getEndereco().setCreationDate(hoje);
            }
            
            // Definir nome da pessoa se não fornecido
            if (usuario.getPessoa().getNome() == null || usuario.getPessoa().getNome().trim().isEmpty()) {
                usuario.getPessoa().setNome(usuario.getNome());
            }
            if (usuario.getPessoa().getSegundoNome() == null || usuario.getPessoa().getSegundoNome().trim().isEmpty()) {
                usuario.getPessoa().setSegundoNome("");
            }
        }

        return usuarioRepository.save(usuario);
    }

    public Usuario update(Long id, Usuario usuario) {
        Usuario update = findById(id);

        update.setEmail(usuario.getEmail());
        update.setNomeUser(usuario.getNomeUser());
        update.setNome(usuario.getNome());
        update.setSenha(usuario.getSenha());
        update.setTipoAcesso(usuario.getTipoAcesso());
        update.setPerfil(usuario.getPerfil());
        update.setAtivo(usuario.getAtivo());

        Date hoje = UtilService.getDataAtual();
        update.setLastUpdateDate(hoje);

        // Atualizar pessoa se existir
        if (usuario.getPessoa() != null && update.getPessoa() != null) {
            update.getPessoa().setNome(usuario.getPessoa().getNome());
            update.getPessoa().setSegundoNome(usuario.getPessoa().getSegundoNome());
            update.getPessoa().setCpf(usuario.getPessoa().getCpf());
            update.getPessoa().setDataNascimento(usuario.getPessoa().getDataNascimento());
            update.getPessoa().setTelefone(usuario.getPessoa().getTelefone());
            update.getPessoa().setEmail(usuario.getPessoa().getEmail());
            update.getPessoa().setLastUpdateDate(hoje);
            
            // Definir nome da pessoa se não fornecido
            if (update.getPessoa().getNome() == null || update.getPessoa().getNome().trim().isEmpty()) {
                update.getPessoa().setNome(update.getNome());
            }
            if (update.getPessoa().getSegundoNome() == null || update.getPessoa().getSegundoNome().trim().isEmpty()) {
                update.getPessoa().setSegundoNome("");
            }
            
            // Atualizar endereço se existir
            if (usuario.getPessoa().getEndereco() != null && update.getPessoa().getEndereco() != null) {
                update.getPessoa().getEndereco().setLogradouro(usuario.getPessoa().getEndereco().getLogradouro());
                update.getPessoa().getEndereco().setNumero(usuario.getPessoa().getEndereco().getNumero());
                update.getPessoa().getEndereco().setComplemento(usuario.getPessoa().getEndereco().getComplemento());
                update.getPessoa().getEndereco().setBairro(usuario.getPessoa().getEndereco().getBairro());
                update.getPessoa().getEndereco().setCidade(usuario.getPessoa().getEndereco().getCidade());
                update.getPessoa().getEndereco().setEstado(usuario.getPessoa().getEndereco().getEstado());
                update.getPessoa().getEndereco().setCep(usuario.getPessoa().getEndereco().getCep());
                update.getPessoa().getEndereco().setLastUpdateDate(hoje);
            }
        }

        return usuarioRepository.save(update);
    }

    public void delete(Long id) {
        Usuario usuario = findById(id);
        usuarioRepository.delete(usuario);
    }

    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
    
}
