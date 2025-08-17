package com.example.away.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.*;

@Data
@Entity
@Table(name = "assistido")
public class Assistido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    idAssistido; // Primary Key

    private String  numAuto;
    private String  numProcesso;
    private String  observacao;

    // Campos para Auditoria
    private Integer createdBy;
    private Date    creationDate;
    private Integer lastUpdatedBy;
    private Date    lastUpdateDate;

    // Foreign Key's
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "assistido")
    private Pessoa pessoa;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "assistido")
    private List<Comparecimento> comparecimentos;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idTipoMonitoramento")
    private TipoMonitoramento tipoMonitoramento;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idTipoRegime")
    private TipoRegime tipoRegime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idTipoSituacao")
    private TipoSituacao tipoSituacao;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idVaraExecPenal")
    private VaraExecPenal varaExecPenal;

}