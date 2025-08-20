package com.example.away.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.*;

@Data
@Entity
@Table(name = "pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    idPessoa; // Primary Key

    @NotEmpty(message = "O CPF é obrigatório")
    private String  cpf;

    @NotEmpty(message = "Nome é obrigatório")
    private String  nome;

    @NotEmpty(message = "Segundo Nome é obrigatório")
    private String  segundoNome;

    //@NotEmpty(message = "Data de Nascimento é obrigatório")
    private Date    dataNascimento;

    @NotEmpty(message = "Telefone é obrigatório")
    private String  telefone;

    // Campos para Auditoria
    private Integer createdBy;
    private Date    creationDate;
    private Integer lastUpdatedBy;
    private Date    lastUpdateDate;

    // Foreign Key
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idEndereco")
    private Endereco endereco;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idAssistido")
    private Assistido assistido;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

}
