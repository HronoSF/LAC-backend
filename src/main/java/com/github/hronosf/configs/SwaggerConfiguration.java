package com.github.hronosf.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.URI;

@Configuration
public class SwaggerConfiguration implements WebMvcConfigurer {

    @Value("${security.authorization-endpoint}")
    private URI authEndpoint;

    @Value("${security.token-endpoint}")
    private URI tokenEndpoint;

    private static final String OAUTH_SCHEMA = "OAuth";
    private static final String HTTP_SCHEMA = "Access token";

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement()
                        .addList(OAUTH_SCHEMA)
                        .addList(HTTP_SCHEMA))
                .components(new Components()
                        .addSecuritySchemes(OAUTH_SCHEMA, oauthSecuritySchema())
                        .addSecuritySchemes(HTTP_SCHEMA, httpScheme()));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/ui/**")
                .addResourceLocations("classpath:/META-INF/resources/ui/");
    }

    private SecurityScheme oauthSecuritySchema() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.OAUTH2)
                .flows(new OAuthFlows()
                        .implicit(new OAuthFlow()
                                .authorizationUrl(authEndpoint.toString())
                                .tokenUrl(tokenEndpoint.toString())));
    }

    private SecurityScheme httpScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
    }
}