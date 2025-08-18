package com.example.away.service;

import java.sql.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import com.example.away.model.TipoSituacao;
import com.example.away.repository.TipoSituacaoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TipoSituacaoService {
    
    private final TipoSituacaoRepository situacaoRepository;

    public TipoSituacaoService(TipoSituacaoRepository situacaoRepository) {
        this.situacaoRepository = situacaoRepository;
    }
    
    public List<TipoSituacao> findAll() {
        return situacaoRepository.findAll();
    }

    public TipoSituacao findById(Long id) {
        return situacaoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public TipoSituacao save(TipoSituacao situacao) {
        Date hoje = UtilService.getDataAtual();

        situacao.setCreatedBy(1);
        situacao.setCreationDate(hoje);

        return situacaoRepository.save(situacao);
    }

    public TipoSituacao update(Long id, TipoSituacao situacao) {
        TipoSituacao update = findById(id);

        // TODO: Formular as validações

        Date hoje = UtilService.getDataAtual();
        situacao.setLastUpdateDate(hoje);

        return situacaoRepository.save(update);
    }

    public void delete(Long id) {
        TipoSituacao tipoSituacao = findById(id);
        situacaoRepository.delete(tipoSituacao);
    }
}
