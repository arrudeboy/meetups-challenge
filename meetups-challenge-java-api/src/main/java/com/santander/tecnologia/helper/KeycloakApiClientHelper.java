package com.santander.tecnologia.helper;

import com.santander.tecnologia.model.User;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import java.util.Collections;

@PropertySource("classpath:application.properties")
@Component
public class KeycloakApiClientHelper {

    private final Keycloak keycloakInstance;
    @Value("${keycloak.realm}")
    private String realm;

    public KeycloakApiClientHelper(Keycloak keycloakInstance) {
        this.keycloakInstance = keycloakInstance;
    }

    private UsersResource getUserResources() {
        return keycloakInstance.realm(realm).users();
    }

    private RoleRepresentation getMeetupUserRole() {
        return keycloakInstance.realm(realm).roles().get("meetup_user").toRepresentation();
    }

    private CredentialRepresentation setUserPassword(String password) {

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);

        return credentialRepresentation;
    }

    public void createUser(User user) {

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(user.getUsername());
        userRepresentation.setEmail(user.getEmail());
        Response response = getUserResources().create(userRepresentation);

        // after user creation we set password and assign role
        String userId = CreatedResponseUtil.getCreatedId(response);
        UserResource userResource = getUserResources().get(userId);
        userResource.roles().realmLevel().add(Collections.singletonList(getMeetupUserRole()));
        userResource.resetPassword(setUserPassword(user.getPassword()));
    }

    public void updateUser(User user) {

        UsersResource usersResource = getUserResources();
        UserRepresentation userRepresentation = usersResource.search(user.getUsername()).get(0);
        userRepresentation.setEmail(user.getEmail());
        UserResource userResource = usersResource.get(userRepresentation.getId());
        userResource.resetPassword(setUserPassword(user.getPassword()));
        userResource.update(userRepresentation);
    }

    public void deleteUser(String username) {

        UsersResource usersResource = getUserResources();
        String id = usersResource.search(username).get(0).getId();
        UserResource userResource = usersResource.get(id);
        userResource.remove();
    }
}
