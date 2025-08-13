package com.example.away.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.away.model.VaraExecPenal;
import com.example.away.repository.VaraExecPenalRepository;

@Service
public class VaraExecPenalService {
    
    private final VaraExecPenalRepository varaPenalRepository;

    public VaraExecPenalService(VaraExecPenalRepository varaPenalRepository) {
        this.varaPenalRepository = varaPenalRepository;
    }
    
    public List<VaraExecPenal> findAll() {
        return varaPenalRepository.findAll();
    }
}
