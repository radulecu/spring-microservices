package ro.rasel.spring.microservices.ssoauthservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.cache.NullUserCache;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class AuthenticationProviderService implements AuthenticationProvider, MessageSourceAware {
    protected static final Logger logger = LoggerFactory.getLogger(AuthenticationProviderService.class);
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private final UserCache userCache;
    private final UserDetailsService userDetailsService;

    public AuthenticationProviderService(
            @Autowired(required = false) UserCache userCache,
            UserDetailsService userDetailsService) {
        this.userCache = userCache != null ? userCache : new NullUserCache();
        this.userDetailsService = userDetailsService;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication,
                () -> this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.onlySupports",
                        "Only UsernamePasswordAuthenticationToken is supported"));
        String username = authentication.getPrincipal() == null ? "NONE_PROVIDED" : authentication.getName();
        UserDetails userDetails = this.userCache.getUserFromCache(username);

        if (validUser(userDetails)) {
            userDetails = this.retrieveUser(username, (UsernamePasswordAuthenticationToken) authentication);
            this.userCache.putUserInCache(userDetails);
        }

        validateUser(userDetails);
        return this.createSuccessAuthentication(userDetails, authentication, userDetails);
    }

    private UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        try {
            return userDetailsService.loadUser(username, (CharSequence) authentication.getCredentials());
        } catch (UsernameNotFoundException e) {
            return null;
        } catch (InternalAuthenticationServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(messages
                    .getMessage(this.getClass().getName() + "unexpectedError", "Unexpected error"), e);
        }
    }

    private Authentication createSuccessAuthentication(
            Object principal, Authentication authentication, UserDetails user) {
        UsernamePasswordAuthenticationToken result =
                new UsernamePasswordAuthenticationToken(principal, authentication.getCredentials(),
                        user.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }

    private boolean validUser(UserDetails user) {
        return user == null
                || !user.isEnabled()
                || !user.isAccountNonLocked()
                || !user.isAccountNonExpired()
                || !user.isCredentialsNonExpired();
    }

    private void validateUser(UserDetails userDetails) {
        if (userDetails == null) {
            logger.debug("Authentication failed");
            throw new BadCredentialsException(this.messages
                    .getMessage(this.getClass().getName() + ".badCredentials", "Bad credentials"));
        }

        checkAccountEnabled(userDetails);
        checkAccountNotLocked(userDetails);
        checkAccountNotExpired(userDetails);
        checkCredentialsExpired(userDetails);
    }

    private void checkAccountEnabled(UserDetails user) {
        if (!user.isEnabled()) {
            logger.debug("User account is disabled");
            throw new DisabledException(messages
                    .getMessage(this.getClass().getName() + ".disabled", new String[]{user.getUsername()},
                            "User account for username {0} is disabled"));
        }
    }

    private void checkAccountNotExpired(UserDetails user) {
        if (!user.isAccountNonExpired()) {
            logger.debug("User account is expired");
            throw new AccountExpiredException(messages
                    .getMessage(this.getClass().getName() + ".expired", new String[]{user.getUsername()},
                            "User account for username {0} has expired"));
        }
    }

    private void checkAccountNotLocked(UserDetails user) {
        if (!user.isAccountNonLocked()) {
            logger.debug("User account is locked");
            throw new LockedException(messages
                    .getMessage(this.getClass().getName() + ".locked", new String[]{user.getUsername()},
                            "User account for username {0} is locked"));
        }
    }

    private void checkCredentialsExpired(UserDetails user) {
        if (!user.isCredentialsNonExpired()) {
            logger.debug("User account credentials have expired");
            throw new CredentialsExpiredException(messages
                    .getMessage(this.getClass().getName() + ".credentialsExpired",
                            new String[]{user.getUsername()}, "User credentials for username {0} have expired"));
        }
    }
}
