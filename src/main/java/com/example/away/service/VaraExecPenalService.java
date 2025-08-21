package com.example.away.service;

import java.sql.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import com.example.away.model.VaraExecPenal;
import com.example.away.repository.VaraExecPenalRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class VaraExecPenalService {
    
    private final VaraExecPenalRepository varaPenalRepository;

    public VaraExecPenalService(VaraExecPenalRepository varaPenalRepository) {
        this.varaPenalRepository = varaPenalRepository;
    }
    
    public List<VaraExecPenal> findAll() {
        return varaPenalRepository.findAll();
    }

    public VaraExecPenal findById(Long id) {
        return varaPenalRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public VaraExecPenal save(VaraExecPenal varaPenal) {
        Date hoje = UtilService.getDataAtual();

        varaPenal.setCreatedBy(1);
        varaPenal.setCreationDate(hoje);

        return varaPenalRepository.save(varaPenal);
    }

    public VaraExecPenal update(Long id, VaraExecPenal varaPenal) {
        VaraExecPenal update = findById(id);

        update.setNome(varaPenal.getNome());
        update.setDescricao(varaPenal.getDescricao());

        Date hoje = UtilService.getDataAtual();
        varaPenal.setLastUpdateDate(hoje);

        return varaPenalRepository.save(update);
    }

    public void delete(Long id) {
        VaraExecPenal varaExecPenal = findById(id);
        varaPenalRepository.delete(varaExecPenal);
    }
}
