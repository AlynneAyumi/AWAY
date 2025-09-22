package com.example.away.dto;

import lombok.Data;

@Data
public class AssistidoUpdateRequest {
    private String numAuto;
    private String numProcesso;
    private String observacao;
    private PessoaUpdateDTO pessoa;

    @Data
    public static class PessoaUpdateDTO {
        private Long idPessoa;
        private String nome;
        private String segundoNome;
        private String cpf;
        private String dataNascimento;
        private String telefone;
        private EnderecoUpdateDTO endereco;
    }

    @Data
    public static class EnderecoUpdateDTO {
        private Long idEndereco;
        private String logradouro;
        private String cep;
        private String bairro;
        private String cidade;
        private String estado;
        private Integer numero;
    }
}
