package com.example.away.service;

import java.sql.Date;
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

}
