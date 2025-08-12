package com.example.away.model;

import java.time.LocalDate;

public class Pessoa {
    private Long id;
    private String cpf;
    private LocalDate dataNascimento;
    private String nome;
    private String telefone;
    private Long idEndereco;
    private String segundoNome;

    //Getters e Setters

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Long getIdEndereco() {
        return idEndereco;
    }
    public void setIdEndereco(Long idEndereco) {
        this.idEndereco = idEndereco;
    }

    public String getSegundoNome() {
        return segundoNome;
    }
    public void setSegundoNome(String segundoNome) {
        this.segundoNome = segundoNome;
    }

}
