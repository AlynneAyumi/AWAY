package com.example.away.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.away.model.Comparecimento;
import com.example.away.repository.ComparecimentoRepository;

@Service
public class ComparecimentoService {
    
    private final ComparecimentoRepository comparecimentoRepository;

    public ComparecimentoService(ComparecimentoRepository comparecimentoRepository) {
        this.comparecimentoRepository = comparecimentoRepository;
    }
    
    public List<Comparecimento> findAll() {
        return comparecimentoRepository.findAll();
    }
}
