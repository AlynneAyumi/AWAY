package com.example.away.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    // Situação de comparecimento PENDENTE/COMPARECEU
    private EnumSituacao statusComparecimento;

    // Campos para Auditoria
    private Integer createdBy;
    private Date    creationDate;
    private Integer lastUpdatedBy;
    private Date    lastUpdateDate;

    // Foreign Key's
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "assistido")
    @JsonBackReference
    private Pessoa pessoa;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "assistido")
    @JsonManagedReference
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