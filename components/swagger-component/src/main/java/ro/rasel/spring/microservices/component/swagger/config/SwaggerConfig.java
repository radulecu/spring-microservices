package ro.rasel.spring.microservices.component.swagger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.rasel.spring.microservices.common.utils.connection.securityclient.SecurityConfig;
import ro.rasel.spring.microservices.component.swagger.config.properties.SwaggerConfigProperties;
import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.TokenEndpoint;
import springfox.documentation.service.TokenRequestEndpoint;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Configuration
public class SwaggerConfig {
    public static final String OAUTH2_AUTHORISATION_CODE_SECURITY_SCHEME_NAME = "oauth2_authorisation_code";
    public static final String OAUTH2_PASSWORD_SECURITY_SCHEME_NAME = "oauth2_password";
    public static final String READ_AUTHORIZATION_SCOPE = "read";
    public static final String WRITE_AUTHORIZATION_SCOPE = "write";
    public static final String OPENID_AUTHORIZATION_SCOPE = "openid";
    private final SwaggerConfigProperties swaggerConfigProperties;
    private final SecurityConfig securityConfig;

    @Autowired
    public SwaggerConfig(
            SwaggerConfigProperties swaggerConfigProperties,
            @Autowired(required = false) SecurityConfig securityConfig) {
        this.swaggerConfigProperties = swaggerConfigProperties;
        this.securityConfig = securityConfig;
    }

    @Bean
    public Docket api() {
        Set<String> applicationJson = new HashSet<>(Collections.singletonList(APPLICATION_JSON_VALUE));
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerConfigProperties.isEnabled())
                .consumes(applicationJson)
                .produces(applicationJson)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ro.rasel"))
                .paths(PathSelectors.any())
                .build();
        if (userAuthConfigured()) {
            docket = docket
                    .securityContexts(Collections.singletonList(securityContext()))
                    .securitySchemes(Arrays.asList(authorizationCodeSecurityScheme(),passwordSecurityScheme()));
        }
        return docket;
    }

    @Bean
    public SecurityConfiguration security() {
        if (userAuthConfigured()) {
            return SecurityConfigurationBuilder.builder()
                    .clientId(securityConfig.getUser())
                    .clientSecret(securityConfig.getPassword())
                    .scopeSeparator(" ")
                    .useBasicAuthenticationWithAccessCodeGrant(true)
                    .build();
        } else {
            return null;
        }
    }

    private boolean userAuthConfigured() {
        return securityConfig != null && securityConfig.getUser() != null && securityConfig.getPassword() != null &&
                securityConfig.getUrl() != null;
    }

    private SecurityScheme passwordSecurityScheme() {

        List<AuthorizationScope> authorizationScopeList = Arrays.asList(scopes());

        List<GrantType> grantTypes = new ArrayList<>();
        GrantType passwordCredentialsGrant =
                new ResourceOwnerPasswordCredentialsGrant(securityConfig.getUrl() + "/oauth/token");
        grantTypes.add(passwordCredentialsGrant);

        return new OAuth("oauth2_password", authorizationScopeList, grantTypes);
    }

    private SecurityScheme authorizationCodeSecurityScheme() {
        GrantType grantType = new AuthorizationCodeGrantBuilder()
                .tokenEndpoint(new TokenEndpoint(securityConfig.getUrl() + "/oauth/token", "oauthtoken"))
                .tokenRequestEndpoint(
                        new TokenRequestEndpoint(securityConfig.getUrl() + "/oauth/authorize",
                                securityConfig.getUser() + "test",
                                securityConfig.getPassword()))
                .build();

        return new OAuthBuilder().name(OAUTH2_AUTHORISATION_CODE_SECURITY_SCHEME_NAME)
                .grantTypes(Collections.singletonList(grantType))
                .scopes(Arrays.asList(scopes()))
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(Arrays.asList(
                        new SecurityReference(OAUTH2_PASSWORD_SECURITY_SCHEME_NAME, scopes()),
                        new SecurityReference(OAUTH2_AUTHORISATION_CODE_SECURITY_SCHEME_NAME, scopes())))
                .forPaths(PathSelectors.any())
                .build();
    }

    private AuthorizationScope[] scopes() {
        return new AuthorizationScope[]{
                new AuthorizationScope(READ_AUTHORIZATION_SCOPE, "for read operations"),
                new AuthorizationScope(WRITE_AUTHORIZATION_SCOPE, "for write operations")};
    }
}