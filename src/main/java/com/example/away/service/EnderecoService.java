package com.example.away.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.away.model.Endereco;
import com.example.away.repository.EnderecoRepository;

@Service
public class EnderecoService {
    
    private final EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }
    
    public List<Endereco> findAll() {
        return enderecoRepository.findAll();
    }
}
