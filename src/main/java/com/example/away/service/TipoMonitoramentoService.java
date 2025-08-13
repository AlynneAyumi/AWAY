package com.example.away.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.away.model.TipoMonitoramento;
import com.example.away.repository.TipoMonitoramentoRespository;

@Service
public class TipoMonitoramentoService {
    
    private final TipoMonitoramentoRespository monitoramentoRepository;

    public TipoMonitoramentoService(TipoMonitoramentoRespository monitoramentoRepository) {
        this.monitoramentoRepository = monitoramentoRepository;
    }
    
    public List<TipoMonitoramento> findAll() {
        return monitoramentoRepository.findAll();
    }
}
