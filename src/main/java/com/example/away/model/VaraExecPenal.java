package com.example.away.model;

import java.time.LocalDate;

public class VaraExecPenal {
    private Long idVaraExecPenal;
    private String createdBy;
    private LocalDate creationDate;
    private String descricao;
    private LocalDate lastUpdateDate;
    private String lastUpdateBy;
    private String nome;

    //Getter e Setters

    public Long getIdVaraExecPenal() {
        return idVaraExecPenal;
    }
    public void setIdVaraExecPenal(Long idVaraExecPenal) {
        this.idVaraExecPenal = idVaraExecPenal;
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

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getLastUpdateDate() {
        return lastUpdateDate;
    }
    public void setLastUpdateDate(LocalDate lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }
    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

}
