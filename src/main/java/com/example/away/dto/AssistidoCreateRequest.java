package com.example.away.dto;

import lombok.Data;

@Data
public class AssistidoCreateRequest {
    private String numAuto;
    private String numProcesso;
    private String observacao;
    
    // Dados da pessoa
    private PessoaData pessoa;
    
    @Data
    public static class PessoaData {
        private String nome;
        private String segundoNome;
        private String cpf;
        private String dataNascimento;
        private String telefone;
        private EnderecoData endereco;
    }
    
    @Data
    public static class EnderecoData {
        private String logradouro;
    }
}
