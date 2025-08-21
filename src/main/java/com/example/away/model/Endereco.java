package com.example.away.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.*;

@Data
@Entity
@Table(name = "endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long    idEndereco; // Primary Key

    //@NotEmpty(message = "CEP é obrigatório")
    private String  cep;

    //@NotEmpty(message = "Bairro é obrigatório")
    private String  bairro;

    //@NotEmpty(message = "Cidade é obrigatório")
    private String  cidade;

    //@NotEmpty(message = "Estado é obrigatório")
    private String  estado;

    //@NotEmpty(message = "Nome da Rua é obrigatório")
    private String  rua;

    //@NotEmpty(message = "Número da residência é obrigatório")
    private Integer numero;


    // Campos para Auditoria
    private Integer createdBy;
    private Date    creationDate;
    private Integer lastUpdatedBy;
    private Date    lastUpdateDate;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "endereco")
    private List<Pessoa> pessoas;

}
