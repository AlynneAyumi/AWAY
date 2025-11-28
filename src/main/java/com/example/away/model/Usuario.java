package com.example.away.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

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


    // Retorna as permissões/cargos do usuário (Authorities).
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + perfil));
    }

    // Retorna a senha usada para autenticar o usuário
    @Override
    public String getPassword() {
        return senha;
    }

    // Retorna o nome de usuário único usado para autenticar o usuário
    @Override
    public String getUsername() {
        return email;
    }

    // Indica se a conta do usuário expirou
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Indica se o usuário está bloqueado
    @Override
    public boolean isAccountNonLocked() {
        // Se 'ativo' for TRUE, a conta NÃO está bloqueada
        return this.ativo != null && this.ativo;
    }

    // Indica se as credenciais (senha) do usuário expiraram
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Indica se o usuário está ativo/habilitado
    @Override
    public boolean isEnabled() {
        // Retorna o status de ativo
        return this.ativo != null && this.ativo;
    }
}
