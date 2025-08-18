package com.example.away.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.away.model.TipoSituacao;
import com.example.away.repository.TipoSituacaoRespository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class TipoSituacaoService {
    
    private final TipoSituacaoRespository situacaoRepository;

    public TipoSituacaoService(TipoSituacaoRespository situacaoRepository) {
        this.situacaoRepository = situacaoRepository;
    }
    
    public List<TipoSituacao> findAll() {
        return situacaoRepository.findAll();
    }

    public TipoSituacao findById(Long id) {
        return situacaoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public TipoSituacao save(TipoSituacao situacao) {
        return situacaoRepository.save(situacao);
    }

    public TipoSituacao update(Long id, TipoSituacao situacao) {
        TipoSituacao update = findById(id);

        // TODO: Formular as validações

        return situacaoRepository.save(update);
    }
}
