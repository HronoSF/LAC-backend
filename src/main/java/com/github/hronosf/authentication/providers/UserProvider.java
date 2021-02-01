package com.github.hronosf.authentication.providers;

import com.github.hronosf.authentication.JwtUserAuthenticationToken;
import com.github.hronosf.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProvider {

    public void setAuthenticatedUser(Client client, String role) {
        getAuthenticatedUserContext()
                .ifPresent(userContext -> {
                    userContext.setUser(client);
                    userContext.setRole(role);
                });
    }

    public Client getAuthenticatedUser() {
        return (Client) getAuthenticatedUserContext()
                .map(JwtUserAuthenticationToken::getUser)
                .orElse(null);
    }

    public String getAuthenticatedUserRole() {
        return getAuthenticatedUserContext()
                .map(JwtUserAuthenticationToken::getRole)
                .orElse(null);
    }

    private Optional<JwtUserAuthenticationToken> getAuthenticatedUserContext() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(authentication -> authentication instanceof JwtUserAuthenticationToken)
                .map(JwtUserAuthenticationToken.class::cast);
    }
}
