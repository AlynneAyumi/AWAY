package com.example.away.model;

import java.time.LocalDate;


public class Usuario {
    private Long idUsuario;
    private String createdBy;
    private LocalDate creationDate;
    private String email;
    private String lastUpdateBy;
    private LocalDate lastUpdateDate;
    private String nomeUser;
    private String senha;
    private TipoAcesso TipoAcesso; //criar classe ou enum
    private Long idPessoa;

    // Getters e Setters
    public Long getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public LocalDate getLastUpdateDate() {
        return lastUpdateDate;
    }
    public void setLastUpdateDate(LocalDate lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getNomeUser() {
        return nomeUser;
    }
    public void setNomeUser(String nomeUser) {
        this.nomeUser = nomeUser;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public TipoAcesso getTipoAcesso() {
        return tipoAcesso;
    }
    public void setTipoAcesso(TipoAcesso tipoAcesso) {
        this.tipoAcesso = tipoAcesso;
    }

    public Long getIdPessoa() {
        return idPessoa;
    }
    public void setIdPessoa(Long idPessoa) {
        this.idPessoa = idPessoa;
    }


}
