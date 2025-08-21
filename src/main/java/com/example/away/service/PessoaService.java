package com.example.away.service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import com.example.away.service.UtilService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.away.model.Pessoa;
import com.example.away.repository.PessoaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Transactional(readOnly = true)
    public List<Pessoa> findAll() {
        return pessoaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Pessoa findById(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com id: " + id));
    }

    @Transactional
    public Pessoa save(Pessoa pessoa) {
        // valida duplicidade se CPF estiver presente
        if (pessoa.getCpf() != null && pessoaRepository.existsByCpf(pessoa.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado: " + pessoa.getCpf());
        }

        Date hoje = UtilService.getDataAtual();
        pessoa.setCreatedBy(1);
        pessoa.setCreationDate(hoje);

        return pessoaRepository.save(pessoa);
    }

    @Transactional
    public Pessoa update(Long id, Pessoa pessoa) {
        Pessoa update = findById(id);

        // se CPF mudou, valida duplicidade
        if (pessoa.getCpf() != null
                && (update.getCpf() == null || !pessoa.getCpf().equals(update.getCpf()))
                && pessoaRepository.existsByCpf(pessoa.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado: " + pessoa.getCpf());
        }

        if (hasText(pessoa.getNome())) {
            update.setNome(pessoa.getNome());
        }
        if (hasText(pessoa.getCpf())) {
            update.setCpf(pessoa.getCpf());
        }
        if (hasText(pessoa.getEmail())) {
            update.setEmail(pessoa.getEmail());
        }
        if (hasText(pessoa.getTelefone())) {
            update.setTelefone(pessoa.getTelefone());
        }

        // auditoria
        Date hoje = UtilService.getDataAtual();
        update.setLastUpdateDate(hoje);
        update.setLastUpdatedBy(1);

        return pessoaRepository.save(update);
    }

    @Transactional
    public void delete(Long id) {
        Pessoa pessoa = findById(id);
        pessoaRepository.delete(pessoa);
    }

    // ===== Demais métodos personalizados =====

    @Transactional(readOnly = true)
    public Pessoa buscarPessoaPorCpf(String cpf) {
        return pessoaRepository.findByCpfComJPQL(cpf)
                .orElseThrow(() -> new NoSuchElementException("Pessoa com CPF " + cpf + " não encontrada."));
    }

    @Transactional(readOnly = true)
    public List<Pessoa> searchByNome(String termo) {
        return pessoaRepository.findByNomeContainingIgnoreCase(termo);
    }

    @Transactional(readOnly = true)
    public Optional<Pessoa> findByCpf(String cpf) {
        return pessoaRepository.findByCpfComJPQL(cpf);
    }

    private boolean hasText(String s) {
        return s != null && !s.trim().isEmpty();
    }
}