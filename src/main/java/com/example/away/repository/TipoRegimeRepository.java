package com.example.away.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.away.model.TipoRegime;
import java.util.List;

public interface TipoRegimeRepository extends JpaRepository<TipoRegime, Long>{

    // Busca todos os regimes cujo nome contenha o texto informado
    // Exemplo: findByNomeContainingIgnoreCase("aberto") retorna todos que tenham "aberto" no nome
    List<TipoRegime> findByNomeContainingIgnoreCase(String nome);

    // Verifica se já existe um regime com o nome informado (ignora maiúsculas/minúsculas)
    boolean existsByNomeIgnoreCase(String nome);
}