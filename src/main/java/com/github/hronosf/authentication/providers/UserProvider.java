package com.github.hronosf.authentication.providers;

import com.github.hronosf.authentication.JwtUserAuthenticationToken;
import com.github.hronosf.dto.enums.Roles;
import com.github.hronosf.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProvider {

    private final Optional<AuthenticationProvider> optAuthenticationProvider;

    public void setAuthenticatedUser(User user, String role) {
        getAuthenticatedUserContext()
                .ifPresent(userContext -> {
                    userContext.setUser(user);
                    userContext.setRole(role);
                });
    }

    public User getAuthenticatedUser() {
        return (User) getAuthenticatedUserContext()
                .map(JwtUserAuthenticationToken::getUser)
                .orElse(null);
    }

    public String getAuthenticatedUserRole() {
        return getAuthenticatedUserContext()
                .map(JwtUserAuthenticationToken::getRole)
                .orElse(null);
    }

    public boolean isUserClient() {
        return optAuthenticationProvider
                .map(authenticationProvider -> authenticationProvider.hasRole(Roles.CLIENT.getName()))
                .orElse(false);
    }

    public boolean isUserAdministrator() {
        return optAuthenticationProvider
                .map(authenticationProvider -> authenticationProvider.hasRole(Roles.ADMIN.getName()))
                .orElse(false);
    }

    public <T extends User> T getCurrentUserAs(Class<T> clazz) {
        User user = getAuthenticatedUser();
        if (user != null) {
            if (clazz.isAssignableFrom(user.getClass())) {
                return clazz.cast(user);
            } else {
                throw new IllegalArgumentException(
                        "User can't be casted to " + clazz.getName()
                                + ". Original class is " + user.getClass().getName()
                );
            }
        }
        return null;
    }

    private Optional<JwtUserAuthenticationToken> getAuthenticatedUserContext() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(authentication -> authentication instanceof JwtUserAuthenticationToken)
                .map(JwtUserAuthenticationToken.class::cast);
    }
}
