package com.github.hronosf.authentication.filters;

import com.github.hronosf.authentication.providers.AuthenticationProvider;
import com.github.hronosf.authentication.providers.UserProvider;
import com.github.hronosf.exceptions.ClientNotFoundException;
import com.github.hronosf.model.User;
import com.github.hronosf.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final UserProvider userProvider;
    private final AuthenticationProvider authenticationProvider;

    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String phoneNumber = authenticationProvider.getUserName();

        if (!authenticationProvider.isAnonymous() && StringUtils.isNotBlank(phoneNumber)) {
            User user = userProvider.isUserAdministrator() ?
                    new User().setId(UUID.randomUUID().toString())
                            .setPhoneNumber(phoneNumber) :
                    userService.getByPhoneNumber(phoneNumber);

            if (user == null) {
                throw new ClientNotFoundException("номером телефона", phoneNumber);
            }

            userProvider.setAuthenticatedUser(user, authenticationProvider.getCognitoGroup());
        }

        filterChain.doFilter(request, response);
    }
}
