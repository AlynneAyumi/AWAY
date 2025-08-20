package com.example.away.service;

import java.util.*;

import org.springframework.stereotype.Service;
import com.example.away.model.Comparecimento;
import com.example.away.repository.ComparecimentoRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ComparecimentoService {
    
    private final ComparecimentoRepository comparecimentoRepository;

    public ComparecimentoService(ComparecimentoRepository comparecimentoRepository) {
        this.comparecimentoRepository = comparecimentoRepository;
    }
    
    public List<Comparecimento> findAll() {
        return comparecimentoRepository.findAll();
    }

    public Comparecimento findById(Long id) {
        return comparecimentoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public Comparecimento save(Comparecimento comparecimento) {
        Date hoje = UtilService.getDataAtual();

        comparecimento.setCreatedBy(1);
        comparecimento.setCreationDate(hoje);

        return comparecimentoRepository.save(comparecimento);
    }

    public Comparecimento update(Long id, Comparecimento comparecimento) {
        Comparecimento update = findById(id);

        // TODO: Formular as validações

        Date hoje = UtilService.getDataAtual();
        comparecimento.setLastUpdateDate(hoje);

        return comparecimentoRepository.save(update);
    }

    public void delete(Long id) {
        Comparecimento comparecimento = findById(id);
        comparecimentoRepository.delete(comparecimento);
    }

    // Método automático de busca personalizada
    public List<Comparecimento> buscarPorDataComparecimento(Date data){
        return comparecimentoRepository.findByData(data);
    }

}
