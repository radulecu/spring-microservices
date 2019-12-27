package ro.rasel.spring.microserivces.component.securityclient.common.config.async;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ro.rasel.spring.microserivces.commons.utils.async.AsynchronousDataProvider;

@Configuration
public class SecurityAsynchronousDataProviderImpl implements AsynchronousDataProvider<Authentication> {
    @Override
    public Authentication extract() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public void setup(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
