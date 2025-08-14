package com.example.away.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.away.model.Endereco;
import com.example.away.repository.EnderecoRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class EnderecoService {
    
    private final EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }
    
    public List<Endereco> findAll() {
        return enderecoRepository.findAll();
    }

    public Endereco findById(Long id) {
        return enderecoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public Endereco save(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    public Endereco update(Long id, Endereco endereco) {
        Endereco update = findById(id);

        // TODO: Formular as validações

        return enderecoRepository.save(update);
    }
}
