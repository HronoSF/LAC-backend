package com.github.hronosf.authentication;

import com.github.hronosf.dto.enums.Permissions;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter;
    private final Map<String, List<Permissions>> roleToPermissions;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<GrantedAuthority> roles = jwtGrantedAuthoritiesConverter.convert(jwt);

        Set<GrantedAuthority> permissions = roles.stream()
                .map(GrantedAuthority::getAuthority)
                .filter(roleToPermissions::containsKey)
                .flatMap(a -> roleToPermissions.get(a).stream())
                .map(Permissions::toString)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        roles.addAll(permissions);

        return new JwtUserAuthenticationToken(jwt, roles);
    }
}
