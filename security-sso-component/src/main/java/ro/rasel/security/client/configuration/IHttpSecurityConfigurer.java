package ro.rasel.security.client.configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface IHttpSecurityConfigurer {
    void configure(HttpSecurity http) throws Exception;
}
