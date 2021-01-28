package com.github.hronosf.authentication.providers;

import com.github.hronosf.dto.enums.Permissions;
import com.github.hronosf.dto.enums.Roles;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthenticationProvider {

    private final AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();

    public String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        return authenticationTrustResolver.isAnonymous(authentication) ?
                String.valueOf(authentication.getPrincipal()) :
                ((Jwt) authentication.getPrincipal()).getClaimAsString("phone_number");
    }

    public List<Roles> getRoles() {
        return getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(a -> a.startsWith(Roles.getPrefix()))
                .map(Roles::valueOf)
                .collect(Collectors.toList());
    }

    public List<Permissions> getPermissions() {
        return getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(a -> !a.startsWith(Roles.getPrefix()))
                .map(Permissions::valueOf)
                .collect(Collectors.toList());
    }

    public boolean isAnonymous() {
        SecurityContext context = SecurityContextHolder.getContext();
        return context == null || authenticationTrustResolver.isAnonymous(context.getAuthentication());
    }

    public String getCognitoGroup() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authenticationTrustResolver.isAnonymous(authentication)) {
            return authentication.getAuthorities().iterator().next().getAuthority();
        } else {
            List<String> groups = ((Jwt) authentication.getPrincipal()).getClaimAsStringList("cognito:groups");
            return CollectionUtils.isEmpty(groups) ? null : groups.get(0);
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getAuthorities)
                .orElse(Collections.emptyList());
    }
}
