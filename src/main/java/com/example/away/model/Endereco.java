package com.example.away.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.*;

@Data
@Entity
@Table(name = "endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    idEndereco; // Primary Key

    private String  bairro;
    private String  cep;
    private String  cidade;
    private String  estado;
    private Integer numero;
    private String  rua;

    // Campos para Auditoria
    private Integer createdBy;
    private Date    creationDate;
    private Integer lastUpdatedBy;
    private Date    lastUpdateDate;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "endereco")
    private List<Pessoa> pessoas;

}
