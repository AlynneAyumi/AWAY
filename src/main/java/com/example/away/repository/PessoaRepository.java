package com.example.away.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.away.model.Pessoa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

    //Busca pessoa pelo CPF usando JPQL
    @Query("SELECT p FROM Pessoa p WHERE p.cpf = :cpf")
    Optional<Pessoa> findByCpfComJPQL(@Param("cpf") String cpf);

    //Busca parcial por nome, ignorando minusculas/maiusculas
    List<Pessoa> findByNomeContainingIgnoreCase(String nome);

    //verifica se ja tem alguem com o mesmo CPF
    boolean existsByCpf(String cpf);

    //Busca sem JPQL
    //Optional<Pessoa> findByCpf(String cpf);

}
