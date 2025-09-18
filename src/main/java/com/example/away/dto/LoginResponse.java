package com.example.away.dto;

import com.example.away.model.Usuario;
import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private Usuario usuario;
    private Integer expiresIn; // em segundos
}
