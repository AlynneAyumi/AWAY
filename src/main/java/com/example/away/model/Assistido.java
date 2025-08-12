package com.example.away.model;

import jakarta.persistence.Entity;
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

}