package com.example.away.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com id: " + id));
    }

    @Transactional
    public Usuario save(Usuario usuario) {
        // validações de duplicidade (ajuste conforme sua entidade)
        if (usuario.getEmail() != null && usuarioRepository.existsByEmailIgnoreCase(usuario.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado: " + usuario.getEmail());
        }
        if (usuario.getLogin() != null && usuarioRepository.findByLogin(usuario.getLogin()).isPresent()) {
            throw new IllegalArgumentException("Login já cadastrado: " + usuario.getLogin());
        }

        Date hoje = UtilService.getDataAtual();
        usuario.setCreatedBy(1);        // futuramente, id do usuário autenticado
        usuario.setCreationDate(hoje);

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario update(Long id, Usuario usuario) {
        Usuario update = findById(id);

        // se o e-mail mudou, valida duplicidade
        if (usuario.getEmail() != null
                && !usuario.getEmail().equalsIgnoreCase(update.getEmail())
                && usuarioRepository.existsByEmailIgnoreCase(usuario.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado: " + usuario.getEmail());
        }

        // se o login mudou, valida duplicidade
        if (usuario.getLogin() != null
                && (update.getLogin() == null || !usuario.getLogin().equals(update.getLogin()))
                && usuarioRepository.findByLogin(usuario.getLogin()).isPresent()) {
            throw new IllegalArgumentException("Login já cadastrado: " + usuario.getLogin());
        }

        // copia campos editáveis (ajuste conforme os atributos reais da sua entidade)
        if (usuario.getNome() != null && !usuario.getNome().isBlank()) {
            update.setNome(usuario.getNome());
        }
        if (usuario.getEmail() != null && !usuario.getEmail().isBlank()) {
            update.setEmail(usuario.getEmail());
        }
        if (usuario.getLogin() != null && !usuario.getLogin().isBlank()) {
            update.setLogin(usuario.getLogin());
        }
        if (usuario.getSenha() != null && !usuario.getSenha().isBlank()) {
            update.setSenha(usuario.getSenha());
        }
        // se tiver campo "ativo" (Boolean)
        if (usuario.getAtivo() != null) {
            update.setAtivo(usuario.getAtivo());
        }

        // auditoria
        Date hoje = UtilService.getDataAtual();
        update.setLastUpdateDate(hoje);
        update.setLastUpdatedBy(1);

        return usuarioRepository.save(update);
    }

    @Transactional
    public void delete(Long id) {
        Usuario usuario = findById(id);
        usuarioRepository.delete(usuario);
    }

    // ===== pass-through para endpoints extras do controller =====

    public Optional<Usuario> findByLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }

    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmailIgnoreCase(email);
    }
}