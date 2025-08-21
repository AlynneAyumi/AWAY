package com.example.away.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.away.model.Endereco;

import java.util.*;

public interface EnderecoRepository extends JpaRepository<Endereco, Long>{

    // Consultas com Métodos Automáticos
    Optional<Endereco> findByCep(String cep);

    Optional<Endereco> findByCidade(String cidade);
}
