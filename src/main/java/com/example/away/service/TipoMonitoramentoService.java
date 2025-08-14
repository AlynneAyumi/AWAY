package com.example.away.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.away.model.TipoMonitoramento;
import com.example.away.repository.TipoMonitoramentoRespository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class TipoMonitoramentoService {
    
    private final TipoMonitoramentoRespository monitoramentoRepository;

    public TipoMonitoramentoService(TipoMonitoramentoRespository monitoramentoRepository) {
        this.monitoramentoRepository = monitoramentoRepository;
    }
    
    public List<TipoMonitoramento> findAll() {
        return monitoramentoRepository.findAll();
    }

    public TipoMonitoramento findById(Long id) {
        return monitoramentoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public TipoMonitoramento save(TipoMonitoramento monitoramento) {
        return monitoramentoRepository.save(monitoramento);
    }

    public TipoMonitoramento update(Long id, TipoMonitoramento monitoramento) {
        TipoMonitoramento update = findById(id);

        // TODO: Formular as validações

        return monitoramentoRepository.save(update);
    }
}
