package com.example.away.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.away.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
}
