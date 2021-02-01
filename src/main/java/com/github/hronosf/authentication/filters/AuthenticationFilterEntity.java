package com.github.hronosf.authentication.filters;

import com.github.hronosf.authentication.providers.AuthenticationProvider;
import com.github.hronosf.authentication.providers.UserProvider;
import com.github.hronosf.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthenticationFilterEntity {

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> loggingFilter(@Autowired UserService userService,
                                                                      @Autowired UserProvider userProvider,
                                                                      @Autowired AuthenticationProvider authenticationProvider) {
        FilterRegistrationBean<AuthenticationFilter> cognitoAuthBean = new FilterRegistrationBean<>();

        cognitoAuthBean.setFilter(new AuthenticationFilter(userService, userProvider, authenticationProvider));
        cognitoAuthBean.addUrlPatterns("/api/v1/*");

        return cognitoAuthBean;
    }
}
