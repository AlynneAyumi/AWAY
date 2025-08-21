package com.example.away.service;

import java.util.*;
import org.springframework.stereotype.Service;
import com.example.away.model.Assistido;
import com.example.away.repository.AssistidoRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AssistidoService {
    
    private final AssistidoRepository assistidoRepository;

    public AssistidoService(AssistidoRepository assistidoRepository) {
        this.assistidoRepository = assistidoRepository;
    }
    
    public List<Assistido> findAll() {
        return assistidoRepository.findAll();
    }

    public Assistido findById(Long id) {
        return assistidoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public Assistido save(Assistido assistido) {
        Date hoje = UtilService.getDataAtual();

        assistido.setCreatedBy(1);
        assistido.setCreationDate(hoje);
        
        return assistidoRepository.save(assistido);
    }

    public Assistido update(Long id, Assistido assistido) {
        Assistido update = findById(id);

        update.setNumAuto(assistido.getNumAuto());
        update.setNumProcesso(assistido.getNumProcesso());
        update.setObservacao(assistido.getObservacao());
        update.setStatusComparecimento(assistido.getStatusComparecimento());
        
        Date hoje = UtilService.getDataAtual();
        assistido.setLastUpdateDate(hoje);

        return assistidoRepository.save(update);
    }

    public void delete(Long id) {
        Assistido assistido = findById(id);
        assistidoRepository.delete(assistido);
    }
}
