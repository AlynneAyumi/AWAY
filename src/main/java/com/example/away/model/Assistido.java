package com.example.away.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.*;

@Data
@Entity
@Table(name = "assistido")
public class Assistido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    idAssistido; // Primary Key

    private Date    data;
    private String  numAuto;
    private String  numProcesso;
    private String  observacao;

    // Campos para Auditoria
    private Integer createdBy;
    private Date    creationDate;
    private Integer lastUpdatedBy;
    private Date    lastUpdateDate;

    // Foreign Key's
    private Long idTipoMonitoramento;
    private Long idTipoRegime;
    private Long idTipoSituacao;
    private Long idVaraExecPenal;
}