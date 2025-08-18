package com.example.away.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.away.model.Endereco;

import java.util.*;

public interface EnderecoRepository extends JpaRepository<Endereco, Long>{

    Optional<Endereco> findByCep(String cep);
}
