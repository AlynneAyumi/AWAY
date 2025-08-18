package com.example.away.service;

import java.util.*;

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
        Date hoje = UtilService.getDataAtual();

        endereco.setCreatedBy(1);
        endereco.setCreationDate(hoje);

        return enderecoRepository.save(endereco);
    }

    public Endereco update(Long id, Endereco endereco) {
        Endereco update = findById(id);

        // TODO: Formular as validações

        Date hoje = UtilService.getDataAtual();
        endereco.setLastUpdateDate(hoje);

        return enderecoRepository.save(update);
    }

    public void delete(Long id) {
        Endereco endereco = findById(id);
        enderecoRepository.delete(endereco);
    }


    // Método automático de busca personalizada
    public Endereco buscarPorCep(String cep) {

        Optional<Endereco> enderecoOptional = enderecoRepository.findByCep(cep);

        // Lança uma exceção se o endereço não for encontrado
        if (enderecoOptional.isEmpty()) {
            throw new IllegalArgumentException("CEP " + cep + " não encontrado.");
        }

        return enderecoOptional.get();
    }
}
