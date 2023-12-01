package ro.rasel.spring.microservices.ssoauthservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ro.rasel.spring.microservices.ssoauthservice.config.properites.UserAccountConfiguration;
import ro.rasel.spring.microservices.ssoauthservice.domain.AuthenticationInfo;
import ro.rasel.spring.microservices.ssoauthservice.domain.Role;
import ro.rasel.spring.microservices.ssoauthservice.domain.UserInfo;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);
    private final UserInfoService userInfoService;
    private final UserAccountConfiguration userAccountConfiguration;

    @Autowired
    public UserDetailsServiceImpl(
            UserInfoService userInfoService,
            UserAccountConfiguration userAccountConfiguration) {
        this.userInfoService = userInfoService;
        this.userAccountConfiguration = userAccountConfiguration;
    }

    @Override
    @Transactional
    public UserDetails loadUser(String username, CharSequence password) {
        final Optional<UserInfo> userInfoOptional = userInfoService.getUser(username);

        if (userInfoOptional.isPresent()) {
            final UserInfo userInfo = userInfoOptional.get();
            if (password.equals(userInfo.getPassword())) {
                if (isNotLocked(userInfo)) {
                    return login(userInfo);
                } else {
                    return toUserDetails(userInfo);
                }
            } else {
                onInvalidCredentials(userInfo);
                return null;
            }
        } else {
            return null;
        }
    }

    private UserDetails login(UserInfo userInfo) {
        if (isNotLocked(userInfo) && userInfo.getAuthenticationInfo() != null &&
                userInfo.getAuthenticationInfo().getTries() != 0) {
            logger.debug("Reset user tries for user {}", userInfo.getUserName());
            userInfo.getAuthenticationInfo().setTries(0);
            return toUserDetails(userInfoService.createOrUpdateUser(userInfo));
        } else {
            return toUserDetails(userInfo);
        }
    }

    private void onInvalidCredentials(UserInfo userInfo) {
        if (isLocked(userInfo)) {
            return;
        }

        final AuthenticationInfo authenticationInfo =
                Optional.of(userInfo)
                        .map(UserInfo::getAuthenticationInfo)
                        .orElseGet(AuthenticationInfo::new);

        final int tries = authenticationInfo.getTries() + 1;
        if (tries < userAccountConfiguration.getMaximumTries()) {
            logger.debug("Set tries to {} for user {}", tries, userInfo.getUserName());
            authenticationInfo.setTries(tries);
        } else {
            logger.debug("Lock user {}", userInfo.getUserName());
            authenticationInfo
                    .setLockedUntil(LocalDateTime.now().plusMinutes(userAccountConfiguration.getLockTimeInMinutes()));
            authenticationInfo.setTries(0);
        }

        userInfo.setAuthenticationInfo(authenticationInfo);
        userInfoService.createOrUpdateUser(userInfo);
    }

    private static UserDetails toUserDetails(UserInfo userInfo) {
        return new User(userInfo.getUserName(), userInfo.getPassword(),
                userInfo.isEnabled(), userInfo.isEnabled(), userInfo.isEnabled(),
                isNotLocked(userInfo),
                AuthorityUtils.createAuthorityList(
                        userInfo.getRoles().stream().map(Role::getRole).toArray(String[]::new)));
    }

    private static boolean isNotLocked(UserInfo userInfo) {
        return !isLocked(userInfo);
    }

    private static boolean isLocked(UserInfo userInfo) {
        return Optional.of(userInfo)
                .map(UserInfo::getAuthenticationInfo)
                .map(AuthenticationInfo::getLockedUntil)
                .map(lockedUntil -> lockedUntil.isAfter(LocalDateTime.now()))
                .orElse(false);
    }
}
