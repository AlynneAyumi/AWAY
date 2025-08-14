package com.example.away.service;

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
        return varaPenalRepository.save(varaPenal);
    }

    public VaraExecPenal update(Long id, VaraExecPenal varaPenal) {
        VaraExecPenal update = findById(id);

        // TODO: Formular as validações

        return varaPenalRepository.save(update);
    }
}
