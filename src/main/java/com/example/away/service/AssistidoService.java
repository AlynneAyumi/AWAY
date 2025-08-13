package com.example.away.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.away.model.Assistido;
import com.example.away.repository.AssistidoRepository;

@Service
public class AssistidoService {
    
    private final AssistidoRepository assistidoRepository;

    public AssistidoService(AssistidoRepository assistidoRepository) {
        this.assistidoRepository = assistidoRepository;
    }
    
    public List<Assistido> findAll() {
        return assistidoRepository.findAll();
    }
}
