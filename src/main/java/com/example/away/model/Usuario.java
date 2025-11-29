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
    private Long    idUsuario;

    @NotEmpty(message = "E-mail é obrigatório")
    @Column(unique = true, nullable = false)
    private String  email;

    @NotEmpty(message = "Nome do Usuário é obrigatório")
    private String  nomeUser;

    @Column(columnDefinition = "varchar(255) default ''")
    private String  nome;

    @NotEmpty(message = "A senha é obrigatória")
    private String  senha;

    @Column(nullable = false)
    private String role;

    @Column(columnDefinition = "boolean default true")
    private Boolean ativo;

    private Integer createdBy;
    private Date    creationDate;
    private Integer lastUpdatedBy;
    private Date    lastUpdateDate;
    private Integer TipoAcesso;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "idPessoa")
    @JsonManagedReference("usuario-pessoa")
    private Pessoa pessoa;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(role.split(","))
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.trim()))
                .toList();
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.ativo != null && this.ativo;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.ativo != null && this.ativo;
    }
}
