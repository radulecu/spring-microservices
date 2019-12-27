package ro.rasel.spring.microserivces.component.securityclient.common;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface IHttpSecurityConfigurer {
    void configure(HttpSecurity http) throws Exception;
}
