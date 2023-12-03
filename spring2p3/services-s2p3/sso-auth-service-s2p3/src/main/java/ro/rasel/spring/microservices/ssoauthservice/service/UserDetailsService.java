package ro.rasel.spring.microservices.ssoauthservice.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailsService {
    UserDetails loadUser(String username, CharSequence password);
}
