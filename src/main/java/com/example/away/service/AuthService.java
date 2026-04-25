package com.example.away.service;

import com.example.away.dto.LoginRequest;
import com.example.away.dto.LoginResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class AuthService {

    @Value("${keycloak.server-url}")
    private String keycloakURL;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    public LoginResponse login (LoginRequest loginRequest) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            String body = "grant_type=password"
                    + "&client_id=" + clientId
                    + "&client_secret=" + clientSecret
                    + "&username=" + loginRequest.getEmail()
                    + "&password=" + loginRequest.getSenha();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(keycloakURL + "/realms/" + realm + "/protocol/openid-connect/token"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) throw new BadCredentialsException(null);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(response.body());
            String accessToken = json.get("access_token").asText();

            int expiresIn = json.get("expires_in").asInt();

            return new LoginResponse(accessToken, expiresIn);

        } catch (BadCredentialsException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao comunicar com o Keycloak", e);
        }
    }
}
