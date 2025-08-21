package com.example.away.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.away.model.VaraExecPenal;
import java.util.List;

public interface VaraExecPenalRepository extends JpaRepository<VaraExecPenal, Long>{

    // Busca todas as varas cujo nome contenha o texto informado
    // Exemplo: findByNomeContainingIgnoreCase("penal") retorna todas que tenham "penal" no nome
    List<VaraExecPenal> findByNomeContainingIgnoreCase(String nome);

    // Verifica se já existe uma vara com o nome informado
    boolean existsByNomeIgnoreCase(String nome);
}
