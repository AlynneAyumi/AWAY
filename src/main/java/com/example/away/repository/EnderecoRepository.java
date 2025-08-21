package com.example.away.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.away.model.Endereco;

import java.util.*;

public interface EnderecoRepository extends JpaRepository<Endereco, Long>{

    // Buscar Endereço único pelo CEP
    Optional<Endereco> findByCep(String cep);

    // Listar endereços por cidade (busca parcial, ignorando maiúsculas/minúsculas)
    List<Endereco> findByCidadeContainingIgnoreCase(String cidade);

    // Verificar se já existe endereço cadastrado em determinado CEP
    boolean existsByCep(String cep);
}
