package com.example.away.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.*;

@Data
@Entity
@Table(name = "vara_exec_penal")
public class VaraExecPenal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    idVaraExecPenal; // Primary Key

    private String  descricao;
    private String  nome;

    // Campos para Auditoria
    private Integer createdBy;
    private Date    creationDate;
    private Integer lastUpdatedBy;
    private Date    lastUpdateDate;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "assistido")
    private Assistido assistido;
}
