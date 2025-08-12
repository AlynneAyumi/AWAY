package com.example.away.model;

import java.time.LocalDate;

public class TipoRegime {
    private Long idTipoRegime;
    private String createdBy; //creationBy
    private LocalDate creationDate;
    private String descricao;
    private Boolean flagAtivo;
    private String lastUpdateBy;
    private LocalDate lastUpdateDate;

    //Getter e Setter

    public Long getIdTipoRegime() {
        return idTipoRegime;
    }
    public void setIdTipoRegime(Long idTipoRegime) {
        this.idTipoRegime = idTipoRegime;
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

    public Boolean getFlagAtivo() {
        return flagAtivo;
    }
    public void setFlagAtivo(Boolean flagAtivo) {
        this.flagAtivo = flagAtivo;
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

}
