package com.github.hronosf.services.impl;

import com.github.hronosf.dto.TokenResponse;
import com.github.hronosf.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RestTemplate restTemplate;
    private final StateServiceImpl stateService;

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

    @Override
    public String buildAuthUrl(String redirectUrl) {
        String state = stateService.encodeState(redirectUrl);
        return UriComponentsBuilder.fromUri(authEndpoint)
                .queryParam("response_type", "code")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", this.redirectUrl)
                .queryParam("scope", scope)
                .queryParam("state", state)
                .build()
                .toString();
    }

    @Override
    public String buildLogoutUrl(String redirectUrl) {
        return UriComponentsBuilder.fromUri(logoutUrl)
                .queryParam("client_id", clientId)
                .queryParam("logout_uri", redirectUrl)
                .build()
                .toString();
    }

    @Override
    public List<Cookie> deleteCookies() {
        TokenResponse tokenResponse = new TokenResponse(
                StringUtils.EMPTY, StringUtils.EMPTY, StringUtils.EMPTY, 0, StringUtils.EMPTY);

        return createCookie(tokenResponse, 0);
    }

    @Override
    public List<Cookie> createCookiesFromCode(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri", redirectUrl.toString());
        params.add("code", code);

        return createCookie(sendRequestToAwsCognito(params), -1);
    }

    @Override
    public List<Cookie> createCookiesFromRefreshToken(String refreshToken) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token");
        params.add("redirect_uri", redirectUrl.toString());
        params.add("refresh_token", refreshToken);

        return createCookie(sendRequestToAwsCognito(params), -1);
    }

    private List<Cookie> createCookie(TokenResponse tokenResponse, int refreshTokenAge) {
        List<Cookie> cookies = new ArrayList<>();

        Cookie accessToken =
                createSecureCookie("access_token", tokenResponse.getAccessToken(), tokenResponse.getExpiresIn());
        cookies.add(accessToken);

        Cookie idToken = createSecureCookie("id_token", tokenResponse.getIdToken(), tokenResponse.getExpiresIn());
        cookies.add(idToken);

        if (tokenResponse.getRefreshToken() != null) {
            Cookie refreshToken = createSecureCookie("refresh_token",
                    tokenResponse.getRefreshToken(), refreshTokenAge);
            cookies.add(refreshToken);
        }

        return cookies;
    }

    private Cookie createSecureCookie(String name, String value, int age) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setDomain(domain);
        cookie.setMaxAge(age);
        //cookie.setSecure(true);
        return cookie;
    }

    private TokenResponse sendRequestToAwsCognito(MultiValueMap<String, String> body) {
        RequestEntity<MultiValueMap<String, String>> request = RequestEntity.post(tokenEndpoint)
                .contentType(APPLICATION_FORM_URLENCODED)
                .headers(headers -> headers.setBasicAuth(clientId, clientSecret))
                .body(body);

        return restTemplate.exchange(request, TokenResponse.class).getBody();
    }
}
