package com.example.away.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.away.model.Comparecimento;
import java.util.*;

public interface ComparecimentoRepository extends JpaRepository<Comparecimento, Long>{

    // Consultas com Métodos Automáticos
    List<Comparecimento> findByData(Date data);

    List<Comparecimento> findByFlagComparecimento(Boolean flagComparecimento);
    
    @Query("SELECT c FROM Comparecimento c LEFT JOIN FETCH c.assistido a LEFT JOIN FETCH a.pessoa p ORDER BY c.data DESC")
    List<Comparecimento> findAllWithAssistido();
}
