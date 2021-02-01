package com.github.hronosf.controllers;

import com.github.hronosf.dto.State;
import com.github.hronosf.services.AuthService;
import com.github.hronosf.services.StateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    // see: https://docs.aws.amazon.com/cognito/latest/developerguide/authorization-endpoint.html

    private final AuthService authService;
    private final StateService stateService;

    @GetMapping
    @Operation(summary = "Redirects to AWS Cognito service",
            responses = @ApiResponse(responseCode = "302", content = @Content)
    )
    public RedirectView authenticate(@RequestParam String redirectUrl) {
        return new RedirectView(authService.buildAuthUrl(redirectUrl));
    }

    @GetMapping("/callback")
    @Operation(summary = "Handles response from AWS Cognito service",
            responses = @ApiResponse(responseCode = "302", content = @Content)
    )
    public RedirectView callback(@RequestParam String code, @RequestParam String state, HttpServletResponse response) {
        State decodedState = stateService.decodeState(state);

        List<Cookie> cookies = authService.createCookiesFromCode(code);
        cookies.forEach(response::addCookie);

        return new RedirectView(decodedState.getRedirectUri());
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refreshes JWT tokens")
    public void refresh(@RequestBody String refreshToken, HttpServletResponse response) {
        List<Cookie> cookies = authService.createCookiesFromRefreshToken(refreshToken);
        cookies.forEach(response::addCookie);
    }

    @GetMapping("/logout")
    @Operation(summary = "Redirects to logout")
    public RedirectView logout(@RequestParam String redirectUrl, HttpServletResponse response) {
        String logoutUrl = authService.buildLogoutUrl(redirectUrl);
        List<Cookie> cookies = authService.deleteCookies();
        cookies.forEach(response::addCookie);
        return new RedirectView(logoutUrl);
    }
}
