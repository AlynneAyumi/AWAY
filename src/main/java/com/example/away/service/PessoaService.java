package com.example.away.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.away.model.Pessoa;
import com.example.away.repository.PessoaRepository;

@Service
public class PessoaService {
    
    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }
    
    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }
}
