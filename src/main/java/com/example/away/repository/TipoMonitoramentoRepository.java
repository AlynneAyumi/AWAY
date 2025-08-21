package com.example.away.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.away.model.TipoMonitoramento;
import java.util.List;

public interface TipoMonitoramentoRepository extends JpaRepository<TipoMonitoramento, Long>{

    // Busca todos os tipos de monitoramento cujo nome contenha o texto informado
    // Exemplo: findByNomeContainingIgnoreCase("eletrônico") retorna todos que tenham "eletrônico" no nome
    List<TipoMonitoramento> findByNomeContainingIgnoreCase(String nome);

    // Verifica se já existe um tipo de monitoramento com o nome informado
    boolean existsByNomeIgnoreCase(String nome);
}