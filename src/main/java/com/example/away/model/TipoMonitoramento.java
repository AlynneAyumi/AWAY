package com.example.away.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.*;

@Data
@Entity
@Table(name = "tipo_monitoramento")
public class TipoMonitoramento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    idTipoMonitoramento; // Primary Key

    @NotEmpty(message = "Tipo de Monitoramento é obrigatório")
    private String  descricao;
    private Boolean flagAtivo;

    // Campos para Auditoria
    private Integer createdBy;
    private Date    creationDate;
    private Integer lastUpdatedBy;
    private Date    lastUpdateDate;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "tipoMonitoramento")
    private Assistido assistido;

}
