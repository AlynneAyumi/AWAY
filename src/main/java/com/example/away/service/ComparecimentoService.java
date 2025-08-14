package com.example.away.service;

import java.util.List;
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
        return comparecimentoRepository.save(comparecimento);
    }

    public Comparecimento update(Long id, Comparecimento comparecimento) {
        Comparecimento update = findById(id);

        // TODO: Formular as validações

        return comparecimentoRepository.save(update);
    }
}
