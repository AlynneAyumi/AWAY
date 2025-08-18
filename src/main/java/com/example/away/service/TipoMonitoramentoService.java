package com.example.away.service;

import java.sql.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import com.example.away.model.TipoMonitoramento;
import com.example.away.repository.TipoMonitoramentoRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class TipoMonitoramentoService {
    
    private final TipoMonitoramentoRepository monitoramentoRepository;

    public TipoMonitoramentoService(TipoMonitoramentoRepository monitoramentoRepository) {
        this.monitoramentoRepository = monitoramentoRepository;
    }
    
    public List<TipoMonitoramento> findAll() {
        return monitoramentoRepository.findAll();
    }

    public TipoMonitoramento findById(Long id) {
        return monitoramentoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public TipoMonitoramento save(TipoMonitoramento monitoramento) {
        Date hoje = UtilService.getDataAtual();

        monitoramento.setCreatedBy(1);
        monitoramento.setCreationDate(hoje);

        return monitoramentoRepository.save(monitoramento);
    }

    public TipoMonitoramento update(Long id, TipoMonitoramento monitoramento) {
        TipoMonitoramento update = findById(id);

        // TODO: Formular as validações

        Date hoje = UtilService.getDataAtual();
        monitoramento.setLastUpdateDate(hoje);

        return monitoramentoRepository.save(update);
    }

    public void delete(Long id) {
        TipoMonitoramento tipoMonitoramento = findById(id);
        monitoramentoRepository.delete(tipoMonitoramento);
    }
}
