package com.example.away.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    @Column(columnDefinition = "varchar(255) default ''")
    private String  nome;

    @NotEmpty(message = "A senha é obrigatória")
    private String  senha;

    @Column(columnDefinition = "varchar(50) default 'AGENTE'")
    private String  perfil;
    
    @Column(columnDefinition = "boolean default true")
    private Boolean ativo;

    // Campos para Auditoria
    private Integer createdBy;
    private Date    creationDate;
    private Integer lastUpdatedBy;
    private Date    lastUpdateDate;
    private Integer TipoAcesso;

    // Foreign Key's
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "idPessoa")
    @JsonManagedReference("usuario-pessoa")
    private Pessoa pessoa;

}
