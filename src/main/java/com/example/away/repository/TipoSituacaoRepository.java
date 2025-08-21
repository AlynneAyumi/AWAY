package com.example.away.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.away.model.TipoSituacao;
import java.util.List;

public interface TipoSituacaoRepository extends JpaRepository<TipoSituacao, Long>{

    // Busca todas as situações cuja descrição contenha o texto informado
    // Exemplo: findByDescricaoContainingIgnoreCase("prisão") retorna todas que tenham "prisão" no nome
    List<TipoSituacao> findByDescricaoContainingIgnoreCase(String descricao);

    // Verifica se já existe uma situação com a descrição informada
    boolean existsByDescricaoIgnoreCase(String descricao);
}