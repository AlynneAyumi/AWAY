package com.example.away.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.away.model.Usuario;
import com.example.away.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
    
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }
}
