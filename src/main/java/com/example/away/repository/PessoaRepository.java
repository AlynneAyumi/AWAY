package com.example.away.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.away.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
}
