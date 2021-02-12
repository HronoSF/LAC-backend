package com.github.hronosf.controllers;

import com.github.hronosf.IntegrationTest;
import com.github.hronosf.dto.TokenResponse;
import com.github.hronosf.services.StateService;
import com.github.hronosf.util.SecurityProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthControllerTest extends IntegrationTest {

    @Autowired
    private StateService stateService;

    @Autowired
    private SecurityProvider securityProvider;

    private static final String BASE_URL = "/api/v1/auth";

    @Test
    public void shouldRedirectToCognitoAuthEndpointTest() throws Exception {
        String prefix = securityProvider.getAuthEndpoint()
                + "?response_type=code"
                + "&client_id=" + securityProvider.getClientId()
                + "&redirect_uri=" + securityProvider.getRedirectUrl()
                + "&scope=" + securityProvider.getScope()
                + "&state=";

        String redirectUrl = mvc.perform(get(BASE_URL)
                .queryParam("redirectUrl", "http://localhost"))
                .andExpect(status().is3xxRedirection())
                .andReturn()
                .getResponse()
                .getRedirectedUrl();

        assertNotNull(redirectUrl);
        assertTrue(redirectUrl.startsWith(prefix));
    }

    @Test
    void shouldRedirectToSpecifiedUrlWithCookiesTest() throws Exception {
        String redirectUrl = "http://localhost";
        String encodeState = stateService.encodeState(redirectUrl);

        TokenResponse tokenResponse = new TokenResponse("idToken",
                "accessToken",
                "refreshToken",
                10,
                "Bearer");

        when(restTemplate.exchange(any(RequestEntity.class), eq(TokenResponse.class)))
                .thenReturn(ResponseEntity.ok(tokenResponse));

        ResultActions resultActions = mvc.perform(get(BASE_URL + "/callback")
                .queryParam("code", "someCode")
                .queryParam("state", encodeState))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(redirectUrl));

        validateCookies(resultActions, tokenResponse);
    }

    private void validateCookies(ResultActions resultActions, TokenResponse tokenResponse) throws Exception {
        resultActions
                .andExpect(cookie().value("access_token", tokenResponse.getAccessToken()))
                .andExpect(cookie().maxAge("access_token", tokenResponse.getExpiresIn()))
                .andExpect(cookie().domain("access_token", "localhost"))
                .andExpect(cookie().value("id_token", tokenResponse.getIdToken()))
                .andExpect(cookie().maxAge("id_token", tokenResponse.getExpiresIn()))
                .andExpect(cookie().domain("id_token", "localhost"))
                .andExpect(cookie().value("refresh_token", tokenResponse.getRefreshToken()))
                .andExpect(cookie().domain("refresh_token", "localhost"));
    }
}
