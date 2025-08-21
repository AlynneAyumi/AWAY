package com.example.away.service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.example.away.model.Assistido;
import com.example.away.model.Comparecimento;
import com.example.away.model.EnumSituacao;
import com.example.away.repository.AssistidoRepository;
import com.example.away.repository.ComparecimentoRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ComparecimentoService {
    
    private final ComparecimentoRepository comparecimentoRepository;
    private final AssistidoRepository assistidoRepository;

    public ComparecimentoService(ComparecimentoRepository comparecimentoRepository,
                                 AssistidoRepository assistidoRepository) {
        this.comparecimentoRepository = comparecimentoRepository;
        this.assistidoRepository = assistidoRepository;
    }
    
    public List<Comparecimento> findAll() {
        return comparecimentoRepository.findAll();
    }

    public Comparecimento findById(Long id) {
        return comparecimentoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    public Comparecimento save(Comparecimento comparecimento) {
        Date hoje = UtilService.getDataAtual();

        // Verifica se assistido não esta nulo e persiste dentro de comparecimento
        if (comparecimento.getAssistido() != null && comparecimento.getAssistido().getIdAssistido() != null) {
            Assistido assistidoBanco = assistidoRepository
                .findById(comparecimento.getAssistido().getIdAssistido())
                .orElseThrow(() -> new RuntimeException("Assistido não encontrado"));
            
            comparecimento.setAssistido(assistidoBanco);
        } else {
            throw new RuntimeException("Assistido é obrigatório");
        }

        // Persiste a situação de comparecimento
        Assistido assistido = comparecimento.getAssistido();


        // Atualiza último comparecimento
        Date novoComparecimento = comparecimento.getData();
        Date ultimoComparecimento = assistido.getUltimoComparecimento();

        if (ultimoComparecimento == null || novoComparecimento.after(ultimoComparecimento)) {
            assistido.setUltimoComparecimento(novoComparecimento);
        }
        
        //Verifica se o último comparecimento foi a mais de 3 meses
        if (assistido.getUltimoComparecimento() != null) {
            Calendar limite = Calendar.getInstance();
            limite.add(Calendar.MONTH, -3); // 3 meses atrás

            if (assistido.getUltimoComparecimento().before(limite.getTime())) {
                assistido.setStatusComparecimento(EnumSituacao.PENDENTE);
            } else {
                assistido.setStatusComparecimento(EnumSituacao.COMPARECEU);
            }
        } else {
            assistido.setStatusComparecimento(EnumSituacao.PENDENTE);
        }

        // Persiste o assistido atualizado
        assistidoRepository.save(assistido);

        comparecimento.setCreatedBy(1);
        comparecimento.setCreationDate(hoje);

        return comparecimentoRepository.save(comparecimento);
    }

    public Comparecimento update(Long id, Comparecimento comparecimento) {
        Comparecimento update = findById(id);

        if (comparecimento == null) {
            throw new IllegalArgumentException("Comparecimento não pode ser nulo");
        }

        // Validações
        if (comparecimento.getData() != null) {
            update.setData(comparecimento.getData());
        }
        if (comparecimento.getFlagComparecimento() != null) {
            update.setFlagComparecimento(comparecimento.getFlagComparecimento());
        }
        if (comparecimento.getAssistido() != null && comparecimento.getAssistido().getIdAssistido() != null) {
            update.setAssistido(comparecimento.getAssistido());
        }

        Date hoje = UtilService.getDataAtual();
        update.setLastUpdateDate(hoje);
        update.setLastUpdatedBy(1);

        return comparecimentoRepository.save(update);
    }

    public void delete(Long id) {
        Comparecimento comparecimento = findById(id);
        comparecimentoRepository.delete(comparecimento);
    }


    // Consultas com Métodos Automáticos
    public List<Comparecimento> buscarPorDataComparecimento(Date data){
        return comparecimentoRepository.findByData(data);
    }

    public List<Comparecimento> buscarPorFlagComparecimento(Boolean flagComparecimento){
        return comparecimentoRepository.findByFlagComparecimento(flagComparecimento);
    }


}
