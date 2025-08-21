package com.example.away.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.away.model.Assistido;

import java.util.List;

public interface AssistidoRepository extends JpaRepository<Assistido, Long>{

    // Busca por número de processo (com like, ignorando maiúsculas/minúsculas)
    List<Assistido> findByNumProcessoContainingIgnoreCase(String termo);

    // Checagem rápida para evitar duplicidade de processo
    boolean existsByNumProcesso(String numProcesso);

    // Checagem de duplicidade
    boolean existsByNumAuto(String numAuto);

    // Busca por número do auto (ignorando maiúsculas/minúsculas)
    List<Assistido> findByNumAutoContainingIgnoreCase(String termo);
}
