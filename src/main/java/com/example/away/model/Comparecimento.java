package com.example.away.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.ToString;
import java.util.*;

@Data
@Entity
@Table(name = "comparecimento")
public class Comparecimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    idComparecimento; // Primary Key

    @NotNull(message = "Data do Comparecimento é obrigatório")
    private Date    data;

    @NotNull
    private Boolean flagComparecimento;

    private String observacoes;

    // Campos para Auditoria
    private Integer createdBy;
    private Date    creationDate;
    private Integer lastUpdatedBy;
    private Date    lastUpdateDate;

    // Foreign Key
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idAssistido")
    @JsonManagedReference
    @ToString.Exclude
    private Assistido assistido;

}