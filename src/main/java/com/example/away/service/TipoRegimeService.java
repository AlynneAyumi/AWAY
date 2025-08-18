package com.example.away.service;

import java.sql.Date;
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
        Date hoje = UtilService.getDataAtual();

        regime.setCreatedBy(1);
        regime.setCreationDate(hoje); 
        
        return regimeRepository.save(regime);
    }

    public TipoRegime update(Long id, TipoRegime regime) {
        TipoRegime update = findById(id);

        // TODO: Formular as validações

        Date hoje = UtilService.getDataAtual();
        regime.setLastUpdateDate(hoje);

        return regimeRepository.save(update);
    }

    public void delete(Long id) {
        TipoRegime tipoRegime = findById(id);
        regimeRepository.delete(tipoRegime);
    }
}
