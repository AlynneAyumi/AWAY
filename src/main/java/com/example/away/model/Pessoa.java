package com.example.away.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.*;

@Data
@Entity
@Table(name = "pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    idPessoa; // Primary Key

    private String  cpf;
    private Date    dataNascimento;
    private String  nome;
    private String  telefone;
    private String  segundoNome;

    // Campos para Auditoria
    private Integer createdBy;
    private Date    creationDate;
    private Integer lastUpdatedBy;
    private Date    lastUpdateDate;

    // Foreign Key
    private Long    idEndereco;

}
