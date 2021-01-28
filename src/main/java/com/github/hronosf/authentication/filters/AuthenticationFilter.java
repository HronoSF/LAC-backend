package com.github.hronosf.authentication.filters;

import com.github.hronosf.authentication.providers.AuthenticationProvider;
import com.github.hronosf.authentication.providers.UserProvider;
import com.github.hronosf.exceptions.ClientNotActivatedException;
import com.github.hronosf.model.Client;
import com.github.hronosf.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final UserProvider userProvider;
    private final AuthenticationProvider authenticationProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String userName = authenticationProvider.getUserName();

        if (!authenticationProvider.isAnonymous() && StringUtils.isNotBlank(userName)) {
            Client client = userService.getByPhoneNumber(userName);

            if (!client.isActivated()) {
                throw new ClientNotActivatedException(client.getPhoneNumber());
            }

            userProvider.setAuthenticatedUser(client, authenticationProvider.getCognitoGroup());
        }

        filterChain.doFilter(request, response);
    }
}
