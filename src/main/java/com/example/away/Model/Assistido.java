package com.example.away.Model;

import java.time.LocalDate;

public class Assistido {
        private Long idAssistido;
        private String createdBy;
        private LocalDate creationDate;
        private LocalDate data;
        private String lastUpdateBy;
        private LocalDate lastUpdateDate;
        private String numAuto;
        private String numProcesso;
        private String observacao;
        private Long idTipoMonitoramento;
        private Long idTipoRegime;
        private Long idTipoSituacao;
        private Long idVaraExecPenal;

        // Getters e Setters
        public Long getIdAssistido() {
                return idAssistido;
        }
        public void setIdAssistido(Long idAssistido) {
                this.idAssistido = idAssistido;
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

        public int getNumAuto() {
                return numAuto;
        }
        public void setNumAuto(int numAuto) {
                this.numAuto = numAuto;
        }

        public int getNumProcesso() {
                return numProcesso;
        }
        public void setNumProcesso(int numProcesso) {
                this.numProcesso = numProcesso;
        }

        public String getObservacao() {
                return observacao;
        }
        public void setObservacao(String observacao) {
                this.observacao = observacao;
        }

        public Long getIdTipoMonitoramento() {
                return idTipoMonitoramento;
        }
        public void setIdTipoMonitoramento(Long idTipoMonitoramento) {
                this.idTipoMonitoramento = idTipoMonitoramento;
        }

        public Long getIdTipoRegime() {
                return idTipoRegime;
        }
        public void setIdTipoRegime(Long idTipoRegime) {
                this.idTipoRegime = idTipoRegime;
        }

        public Long getIdTipoSituacao() {
                return idTipoSituacao;
        }
        public void setIdTipoSituacao(Long idTipoSituacao) {
                this.idTipoSituacao = idTipoSituacao;
        }

        public Long getIdVaraExecPenal() {
                return idVaraExecPenal;
        }
        public void setIdVaraExecPenal(Long idVaraExecPenal) {
                this.idVaraExecPenal = idVaraExecPenal;
        }


}