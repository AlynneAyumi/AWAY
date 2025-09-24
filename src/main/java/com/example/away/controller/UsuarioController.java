package com.example.away.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.away.model.Usuario;
import com.example.away.service.UsuarioService;
import com.example.away.exception.ResourceNotFoundException;
import com.example.away.exception.BusinessException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/findAll")
    public ResponseEntity<List<Usuario>> findAll() {
        List<Usuario> response = usuarioService.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id) {
        Usuario response = usuarioService.findById(id);
        if (response == null) {
            throw new ResourceNotFoundException("Usuário", "id", id);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/save")
    public ResponseEntity<Usuario> save(@RequestBody Usuario usuario) {
        if (usuario == null) {
            throw new BusinessException("Dados do usuário são obrigatórios", "MISSING_DATA");
        }
        Usuario response = usuarioService.save(usuario);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Long id, @RequestBody Usuario usuario) {
        if (usuario == null) {
            throw new BusinessException("Dados do usuário são obrigatórios", "MISSING_DATA");
        }
        Usuario response = usuarioService.update(id, usuario);
        if (response == null) {
            throw new ResourceNotFoundException("Usuário", "id", id);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        // Verificar se o usuário existe antes de deletar
        Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            throw new ResourceNotFoundException("Usuário", "id", id);
        }
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
