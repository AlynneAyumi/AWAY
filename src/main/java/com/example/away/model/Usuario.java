package com.example.away.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.*;

@Data
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    idUsuario; // Primary Key

    @NotEmpty(message = "E-mail é obrigatório")
    private String  email;

    @NotEmpty(message = "Nome do Usuário é obrigatório")
    private String  nomeUser;

    @NotEmpty(message = "A senha é obrigatória")
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

    public void setLastUpdateDate(java.sql.Date hoje) {
    }
}
