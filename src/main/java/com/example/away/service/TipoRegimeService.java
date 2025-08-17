package com.example.away.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.example.away.model.TipoRegime;
import com.example.away.repository.TipoRegimeRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TipoRegimeService {
    
    private final TipoRegimeRepository regimeRepository;

    public TipoRegimeService(TipoRegimeRepository regimeRepository) {
        this.regimeRepository = regimeRepository;
    }
    
    public List<TipoRegime> findAll() {
        return regimeRepository.findAll();
    }

    public TipoRegime findById(Long id) {
        return regimeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public TipoRegime save(TipoRegime regime) {
        return regimeRepository.save(regime);
    }

    public TipoRegime update(Long id, TipoRegime regime) {
        TipoRegime update = findById(id);

        // TODO: Formular as validações

        return regimeRepository.save(update);
    }

    public void delete(Long id) {
        TipoRegime tipoRegime = findById(id);
        regimeRepository.delete(tipoRegime);
    }
}
