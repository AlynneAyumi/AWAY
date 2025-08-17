package com.example.away.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.*;

@Data
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    idUsuario; // Primary Key

    private String  email;
    private String  nomeUser;
    private String  senha;

    // Campos para Auditoria
    private Integer createdBy;
    private Date    creationDate;
    private Integer lastUpdatedBy;
    private Date    lastUpdateDate;
    private Integer TipoAcesso;

    // Foreign Key's
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "usuario")
    private Pessoa pessoa;

}
