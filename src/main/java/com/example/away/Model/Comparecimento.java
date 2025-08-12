package com.example.away.Model;

import java.time.LocalDate;

public class Comparecimento {
    private Long idComp;
    private String createdBy;
    private LocalDate creationDate;
    private LocalDate data;
    private Boolean flagComparecimento;
    private LocalDate updateDate;
    private String lastUpdatedBy;
    private Long idAssistido;

    //Getters e Setters

    public Long getIdComp() {
        return idComp;
    }
    public void setIdComp(Long idComp) {
        this.idComp = idComp;
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

    public LocalDate getData() {
        return data;
    }
    public void setData(LocalDate data) {
        this.data = data;
    }

    public Boolean getFlagComparecimento() {
        return flagComparecimento;
    }
    public void setFlagComparecimento(Boolean flagComparecimento) {
        this.flagComparecimento = flagComparecimento;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Long getIdAssistido() {
        return idAssistido;
    }
    public void setIdAssistido(Long idAssistido) {
        this.idAssistido = idAssistido;
    }

}