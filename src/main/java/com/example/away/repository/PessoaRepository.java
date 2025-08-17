package com.example.away.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.away.model.Pessoa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

    @Query("SELECT p FROM Pessoa p WHERE p.cpf = :cpf")
    Optional<Pessoa> findByCpfComJPQL(@Param("cpf") String cpf);

}
