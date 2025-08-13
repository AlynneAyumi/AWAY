package com.example.away.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.away.model.TipoSituacao;
import com.example.away.repository.TipoSituacaoRespository;

@Service
public class TipoSituacaoService {
    
    private final TipoSituacaoRespository situacaoRepository;

    public TipoSituacaoService(TipoSituacaoRespository situacaoRepository) {
        this.situacaoRepository = situacaoRepository;
    }
    
    public List<TipoSituacao> findAll() {
        return situacaoRepository.findAll();
    }
}
