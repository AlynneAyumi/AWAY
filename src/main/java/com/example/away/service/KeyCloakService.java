package com.example.away.service;

import jakarta.ws.rs.core.Response;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KeyCloakService {

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.server-url}")
    private String server;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    private Keycloak builder() {
         return KeycloakBuilder.builder()
                .serverUrl(server)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build();
    }

    public String createUser(String name, String email, String password) {

        Keycloak keycloak = this.builder();

        UserRepresentation user = new UserRepresentation();

        user.setEnabled(true);
        user.setUsername(name);
        user.setEmail(email);
        user.setFirstName(name);
        user.setEmailVerified(true);
        user.setRequiredActions(new ArrayList<>());

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);

        user.setCredentials(List.of(credential));

        Response response = keycloak.realm(realm).users().create(user);

        if (response.getStatus() == 201) {
            String path = response.getLocation().getPath();

            return path.substring(path.lastIndexOf("/") + 1);
        }

        throw new RuntimeException("Error create keycloak user");
    }

    public void attachRole(String id, String roleRequest) {

        Keycloak keycloak = this.builder();

        String client = keycloak.realm(realm).clients().findByClientId(id).get(0).getId();
        RoleRepresentation role = keycloak.realm(realm).clients().get(client).roles().get(roleRequest).toRepresentation();

        keycloak.realm(realm).users().get(id).roles().clientLevel(clientId).add(List.of(role));
    }

    public void updateUser(String id, String name, String email) {

        Keycloak keycloak = this.builder();

        UserResource userResource = keycloak.realm(realm).users().get(id);
        UserRepresentation user = userResource.toRepresentation();

        user.setFirstName(name);
        user.setUsername(name);
        user.setEmail(email);

        userResource.update(user);
    }

    public void destroy(String id) {

        Keycloak keycloak = this.builder();

        keycloak.realm(realm).users().get(id).remove();
    }
}
