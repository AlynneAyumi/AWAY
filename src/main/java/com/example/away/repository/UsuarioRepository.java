package com.example.away.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.away.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    // Busca usuário pelo login (exato)
    Optional<Usuario> findByLogin(String login);

    //Verifica se ja existe um usuario com o email informado
    boolean existsByEmailIgnoreCase(String email);

    // Autenticação simples: login + ativo
    Optional<Usuario> findByLoginAndAtivoTrue(String login);

}
