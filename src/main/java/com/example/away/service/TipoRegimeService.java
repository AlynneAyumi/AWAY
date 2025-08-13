package com.example.away.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.away.model.TipoRegime;
import com.example.away.repository.TipoRegimeRespository;

@Service
public class TipoRegimeService {
    
    private final TipoRegimeRespository regimeRepository;

    public TipoRegimeService(TipoRegimeRespository regimeRepository) {
        this.regimeRepository = regimeRepository;
    }
    
    public List<TipoRegime> findAll() {
        return regimeRepository.findAll();
    }
}
