package com.example.away.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;

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

    @Temporal(TemporalType.DATE)
    private Date ultimoComparecimento;

    // Campos para Auditoria
    private Integer createdBy;
    private Date    creationDate;
    private Integer lastUpdatedBy;
    private Date    lastUpdateDate;

    // Foreign Key's
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "idPessoa")
    @JsonManagedReference
    @ToString.Exclude
    private Pessoa pessoa;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "assistido")
    @JsonBackReference
    @ToString.Exclude
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