package com.example.away.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.away.model.Comparecimento;
import java.util.*;

public interface ComparecimentoRepository extends JpaRepository<Comparecimento, Long>{

    // 7. Filtrar por uma data de cadastro específica
    //List<Produto> findByDataComparecimento(Date data);
    List<Comparecimento> findByData(Date data);

}
