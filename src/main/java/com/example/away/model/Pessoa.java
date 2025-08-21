package com.example.away.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
//import lombok.Data;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@Entity
@Table(name = "pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPessoa; // Primary Key

    @NotEmpty(message = "O CPF é obrigatório")
    @Column(unique = true, length = 14, nullable = false)
    private String cpf;

    @NotEmpty(message = "Nome é obrigatório")
    private String nome;

    @NotEmpty(message = "Segundo Nome é obrigatório")
    private String segundoNome;

    @NotNull(message = "Data de Nascimento é obrigatória")
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    @NotEmpty(message = "Telefone é obrigatório")
    private String telefone;

    // Auditoria
    private Integer createdBy;
    private Date creationDate;
    private Integer lastUpdatedBy;
    private Date lastUpdateDate;

    // Foreign Keys
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idEndereco")
    private Endereco endereco;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idAssistido")
    @JsonManagedReference
    private Assistido assistido;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
    private String email;

    // --- Getters/Setters mínimos para compilar sem Lombok ---
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public java.util.Date getLastUpdateDate() { return lastUpdateDate; }
    public void setLastUpdateDate(java.util.Date lastUpdateDate) { this.lastUpdateDate = lastUpdateDate; }

    public Integer getLastUpdatedBy() { return lastUpdatedBy; }
    public void setLastUpdatedBy(Integer lastUpdatedBy) { this.lastUpdatedBy = lastUpdatedBy; }
}