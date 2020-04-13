package ro.rasel.spring.microservices.ssoauthservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import ro.rasel.spring.microservices.ssoauthservice.service.AuthenticationProviderService;

@Configuration
@EnableGlobalAuthentication
public class GlobalAuthenticationConfigurer extends GlobalAuthenticationConfigurerAdapter {

    private final AuthenticationProviderService authenticationProvider;

    public GlobalAuthenticationConfigurer(AuthenticationProviderService authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public void init(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

}
