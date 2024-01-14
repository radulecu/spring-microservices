package ro.rasel.spring.microservices.component.swagger.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ro.rasel.spring.microservices.common.utils.properties.securityclient.SecurityConfigProperties;
import ro.rasel.spring.microservices.component.swagger.config.properties.SwaggerConfigProperties;

import java.util.Optional;

@Configuration
public class SwaggerConfig {
    public static final String OAUTH2_AUTHORISATION_CODE_SECURITY_SCHEME_NAME = "oauth2_authorisation_code";
    public static final String OAUTH2_PASSWORD_SECURITY_SCHEME_NAME = "oauth2_password";
    public static final String READ_AUTHORIZATION_SCOPE = "read";
    public static final String WRITE_AUTHORIZATION_SCOPE = "write";
    private final SwaggerConfigProperties swaggerConfigProperties;
    private final SecurityConfigProperties securityConfig;

    @Autowired
    public SwaggerConfig(
            SwaggerConfigProperties swaggerConfigProperties,
            @Autowired(required = false) SecurityConfigProperties securityConfig) {
        this.swaggerConfigProperties = swaggerConfigProperties;
        this.securityConfig = securityConfig;
    }

    @Bean
    public OpenAPI securityScheme() {
        OpenAPI openApi = new OpenAPI();
        if (userAuthConfigured()) {
            openApi.addSecurityItem(new SecurityRequirement()
                            .addList(OAUTH2_AUTHORISATION_CODE_SECURITY_SCHEME_NAME)
                            .addList(OAUTH2_PASSWORD_SECURITY_SCHEME_NAME))
                    .components(new Components()
                            .addSecuritySchemes(OAUTH2_AUTHORISATION_CODE_SECURITY_SCHEME_NAME,
                                    authorizationCodeSecurityScheme())
                            .addSecuritySchemes(OAUTH2_PASSWORD_SECURITY_SCHEME_NAME, passwordSecurityScheme())
                    );
        }
        Optional.of(swaggerConfigProperties)
                .map(SwaggerConfigProperties::getInfo)
                .map(SwaggerConfigProperties.Info::toSwaggerInfo)
                .ifPresent(openApi::setInfo);
        return openApi;
    }

    private boolean userAuthConfigured() {
        return securityConfig != null && securityConfig.getUser() != null && securityConfig.getPassword() != null &&
                securityConfig.getUrl() != null;
    }

    public SecurityScheme passwordSecurityScheme() {
        OAuthFlows flows = new OAuthFlows();
        flows.password(oAuthFlow());

        SecurityScheme securityScheme = new SecurityScheme();
        securityScheme.setName(OAUTH2_PASSWORD_SECURITY_SCHEME_NAME);
        securityScheme.setType(SecurityScheme.Type.OAUTH2);
        securityScheme.setIn(SecurityScheme.In.HEADER);
        securityScheme.setFlows(flows);

        return securityScheme;
    }

    public SecurityScheme authorizationCodeSecurityScheme() {
        OAuthFlows flows = new OAuthFlows();
        flows.authorizationCode(oAuthFlow());

        SecurityScheme securityScheme = new SecurityScheme();
        securityScheme.setName(OAUTH2_AUTHORISATION_CODE_SECURITY_SCHEME_NAME);
        securityScheme.setType(SecurityScheme.Type.OAUTH2);
        securityScheme.setIn(SecurityScheme.In.HEADER);
        securityScheme.setFlows(flows);

        return securityScheme;
    }

    private OAuthFlow oAuthFlow() {
        OAuthFlow passwordOAuthFlow = new OAuthFlow();
        passwordOAuthFlow.setAuthorizationUrl(securityConfig.getUrl() + "/oauth/authorize");
        passwordOAuthFlow.setTokenUrl(securityConfig.getUrl() + "/oauth/token");
        passwordOAuthFlow.setScopes(scopes());
        return passwordOAuthFlow;
    }

    private Scopes scopes() {
        Scopes scopes = new Scopes();
        scopes.addString(READ_AUTHORIZATION_SCOPE, "for read operations");
        scopes.addString(WRITE_AUTHORIZATION_SCOPE, "for write operations");
        return scopes;
    }
}