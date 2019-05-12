package ro.rasel.security.client.common.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface IHttpSecurityConfigurer {
    void configure(HttpSecurity http) throws Exception;
}
