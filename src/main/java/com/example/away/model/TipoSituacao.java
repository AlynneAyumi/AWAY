package com.example.away.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.*;

@Data
@Entity
@Table(name = "tipo_situacao")
public class TipoSituacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    idTipoSituacao; // Primary Key

    private String  descricao;
    private Boolean flagAtivo;

    // Campos para Auditoria
    private Integer createdBy;
    private Date    creationDate;
    private Integer lastUpdatedBy;
    private Date    lastUpdateDate;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "tipoSituacao")
    private Assistido assistido;
}
