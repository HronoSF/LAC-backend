package com.github.hronosf.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;

@Getter
@Component
public class SecurityProvider {

    @Value("${security.authorization-endpoint}")
    private URI authEndpoint;

    @Value("${security.client-id}")
    private String clientId;

    @Value("${security.client-secret}")
    private String clientSecret;

    @Value("${security.redirect-url}")
    private URI redirectUrl;

    @Value("${security.logout-url}")
    private URI logoutUrl;

    @Value("${security.token-endpoint}")
    private URI tokenEndpoint;

    @Value("${security.domain}")
    private String domain;

    @Value("${security.scope}")
    private String scope;
}
