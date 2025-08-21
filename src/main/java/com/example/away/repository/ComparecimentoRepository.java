package com.example.away.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.away.model.Comparecimento;
import java.util.*;

public interface ComparecimentoRepository extends JpaRepository<Comparecimento, Long>{

    // Filtrar por uma data de cadastro específica
    //List<Produto> findByDataComparecimento(Date data);
    List<Comparecimento> findByData(Date data);

    // Busca todos os comparecimentos de um assistido específico (usando o id da FK)
    List<Comparecimento> findAllByAssistido_IdAssistido(Long idAssistido);

    // Buscar comparecimentos em um intervalo de datas
    List<Comparecimento> findByDataBetween(Date inicio, Date fim);

}
