package com.example.away.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.*;

@Data
@Entity
@Table(name = "vara_exec_penal")
public class VaraExecPenal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    idVaraExecPenal; // Primary Key

    @NotEmpty(message = "Nome da Vara de Execução Penal é obrigatório")
    private String  nome;

    private String  descricao;


    // Campos para Auditoria
    private Integer createdBy;
    private Date    creationDate;
    private Integer lastUpdatedBy;
    private Date    lastUpdateDate;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "varaExecPenal")
    private Assistido assistido;
}
