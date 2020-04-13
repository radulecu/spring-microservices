package ro.rasel.spring.microservices.ssoauthservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ro.rasel.spring.microservices.ssoauthservice.domain.UserInfo;
import ro.rasel.spring.microservices.ssoauthservice.domain.exteptions.EntityAlreadyExistsException;
import ro.rasel.spring.microservices.ssoauthservice.domain.exteptions.EntityDoesNotExistsException;

import java.util.Optional;

public interface UserInfoService {
    Page<UserInfo> getUsers(Pageable pageable) throws UsernameNotFoundException;

    Optional<UserInfo> getUser(String username) throws UsernameNotFoundException;

    UserInfo createOrUpdateUser(boolean exists, boolean notExists, UserInfo userInfo)
            throws EntityDoesNotExistsException, EntityAlreadyExistsException;

    UserInfo createOrUpdateUser(UserInfo userInfo);

    boolean deleteUser(String userId);
}
