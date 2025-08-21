package com.example.away.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.away.model.Assistido;

import java.util.List;

public interface AssistidoRepository extends JpaRepository<Assistido, Long>{

    // Consultas com Métodos Automáticos
    List<Assistido> findByNumAuto(String numAuto);

    List<Assistido> findByNumProcesso(String numProcesso);
}
