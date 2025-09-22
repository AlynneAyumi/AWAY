package com.example.away.service;

import java.util.*;

import com.example.away.model.Comparecimento;
import com.example.away.model.Pessoa;
import com.example.away.model.Endereco;
import org.springframework.stereotype.Service;
import com.example.away.model.Assistido;
import com.example.away.repository.AssistidoRepository;
import com.example.away.repository.ComparecimentoRepository;
import com.example.away.repository.PessoaRepository;
import com.example.away.repository.EnderecoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.jdbc.core.JdbcTemplate;

@Service
public class AssistidoService {
    
    private final AssistidoRepository assistidoRepository;
    
    @Autowired
    private ComparecimentoRepository comparecimentoRepository;
    
    @Autowired
    private PessoaRepository pessoaRepository;
    
    @Autowired
    private EnderecoRepository enderecoRepository;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public AssistidoService(AssistidoRepository assistidoRepository) {
        this.assistidoRepository = assistidoRepository;
    }
    
    public List<Assistido> findAll() {
        List<Assistido> assistidos = assistidoRepository.findAll();
        // Carregar relacionamentos para cada assistido
        for (Assistido assistido : assistidos) {
            if (assistido.getPessoa() != null) {
                // Forçar o carregamento da pessoa e endereço
                assistido.getPessoa().getEndereco();
            }
        }
        return assistidos;
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
        update.setLastUpdateDate(hoje);
        update.setLastUpdatedBy(1);

        return assistidoRepository.save(update);
    }

    @Transactional
    public void delete(Long id) {
        System.out.println("Iniciando exclusão do assistido ID: " + id);
        
        try {
            // 1. Deletar comparecimentos relacionados
            jdbcTemplate.update("DELETE FROM comparecimento WHERE id_assistido = ?", id);
            System.out.println("Deletados comparecimentos relacionados");
            
            // 2. Remover referência de endereço na pessoa
            jdbcTemplate.update("UPDATE pessoa SET id_endereco = NULL WHERE id_assistido = ?", id);
            System.out.println("Removidas referências de endereço na pessoa");
            
            // 3. Deletar endereços usando uma abordagem mais simples
            // Primeiro vamos buscar os IDs dos endereços para deletar
            List<Long> enderecoIds = jdbcTemplate.queryForList(
                "SELECT id_endereco FROM pessoa WHERE id_assistido = ? AND id_endereco IS NOT NULL", 
                Long.class, id);
            
            for (Long enderecoId : enderecoIds) {
                jdbcTemplate.update("DELETE FROM endereco WHERE id_endereco = ?", enderecoId);
            }
            System.out.println("Deletados " + enderecoIds.size() + " endereços relacionados");
            
            // 4. Deletar pessoas relacionadas
            jdbcTemplate.update("DELETE FROM pessoa WHERE id_assistido = ?", id);
            System.out.println("Deletadas pessoas relacionadas");
            
            // 5. Por último, deletar o assistido
            jdbcTemplate.update("DELETE FROM assistido WHERE id_assistido = ?", id);
            System.out.println("Deletado assistido ID: " + id);
            
        } catch (Exception e) {
            System.err.println("Erro ao deletar assistido: " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-throw para que o controller possa capturar
        }
        
        System.out.println("Exclusão do assistido " + id + " concluída com sucesso!");
    }


    // Consultas com Métodos Automáticos
    public List<Assistido> buscarPorNumAuto(String numAuto){
        return assistidoRepository.findByNumAuto(numAuto);
    }

    public List<Assistido> buscarPorNumProcesso(String numProcesso){
        return assistidoRepository.findByNumProcesso(numProcesso);
    }
}
