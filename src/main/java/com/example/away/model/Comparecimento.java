package com.example.away.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.*;

@Data
@Entity
@Table(name = "comparecimento")
public class Comparecimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    idComparecimento; // Primary Key

    private Date    data;
    private Boolean flagComparecimento;

    // Campos para Auditoria
    private Integer createdBy;
    private Date    creationDate;
    private Integer lastUpdatedBy;
    private Date    lastUpdateDate;

    // Foreign Key
    private Long    idAssistido;

}