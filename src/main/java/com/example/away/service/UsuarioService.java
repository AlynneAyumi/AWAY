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

        return usuarioRepository.save(usuario);
    }

    public Usuario update(Long id, Usuario usuario) {
        Usuario update = findById(id);

        update.setEmail(usuario.getEmail());
        update.setNomeUser(usuario.getNomeUser());
        update.setSenha(usuario.getSenha());
        update.setTipoAcesso(usuario.getTipoAcesso());

        Date hoje = UtilService.getDataAtual();
        usuario.setLastUpdateDate(hoje);

        return usuarioRepository.save(update);
    }

    public void delete(Long id) {
        Usuario usuario = findById(id);
        usuarioRepository.delete(usuario);
    }
}
