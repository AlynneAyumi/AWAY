package com.example.away.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.away.model.Comparecimento;
import java.util.*;

public interface ComparecimentoRepository extends JpaRepository<Comparecimento, Long>{

    // Consultas com Métodos Automáticos
    List<Comparecimento> findByData(Date data);

    List<Comparecimento> findByFlagComparecimento(Boolean flagComparecimento);
}
