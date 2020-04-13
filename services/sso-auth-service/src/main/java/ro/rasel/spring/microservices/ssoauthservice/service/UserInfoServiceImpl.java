package ro.rasel.spring.microservices.ssoauthservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.rasel.spring.microservices.ssoauthservice.domain.AuthenticationInfo;
import ro.rasel.spring.microservices.ssoauthservice.domain.UserInfo;
import ro.rasel.spring.microservices.ssoauthservice.domain.exteptions.EntityAlreadyExistsException;
import ro.rasel.spring.microservices.ssoauthservice.domain.exteptions.EntityDoesNotExistsException;
import ro.rasel.spring.microservices.ssoauthservice.repository.UsersRepository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {
    private final UsersRepository usersRepository;
    private final EntityManager entityManager;

    @Autowired
    public UserInfoServiceImpl(UsersRepository usersRepository, EntityManager entityManager) {
        this.usersRepository = usersRepository;
        this.entityManager = entityManager;
    }

    @Override
    public Page<UserInfo> getUsers(Pageable pageable) throws UsernameNotFoundException {
        return usersRepository.findAll(pageable);
    }

    @Override
    public Optional<UserInfo> getUser(String username) throws UsernameNotFoundException {
        return usersRepository.findById(username);
    }

    @Override
    public UserInfo createOrUpdateUser(UserInfo userInfo) {
        return usersRepository.save(
                usersRepository.findById(userInfo.getUserName())
                        .map(userInfoFromDb -> copyUserInfo(userInfo, userInfoFromDb))
                        .orElse(userInfo));
    }

    @Override
    public UserInfo createOrUpdateUser(
            boolean ifExists, boolean ifNotExists, UserInfo userInfo)
            throws EntityDoesNotExistsException, EntityAlreadyExistsException {
        final Optional<UserInfo> userInfoFromDb = usersRepository.findById(userInfo.getUserName());

        return saveUser(ifExists, ifNotExists, userInfo, userInfoFromDb.orElse(null));
    }

    @Override
    public boolean deleteUser(String userId) {
        final Optional<UserInfo> userInfo = usersRepository.findById(userId);
        userInfo.ifPresent(usersRepository::delete);
        return userInfo.isPresent();
    }

    private UserInfo saveUser(
            boolean ifExists, boolean ifNotExists, UserInfo userInfo, UserInfo userInfoFromDb)
            throws EntityDoesNotExistsException, EntityAlreadyExistsException {
        if (userInfoFromDb != null) {
            if (ifExists) {
                copyUserInfo(userInfo, userInfoFromDb);
                return usersRepository.save(userInfoFromDb);
            } else {
                throw new EntityDoesNotExistsException("UserInfo with username:{} does not exist");
            }
        } else {
            if (ifNotExists) {
                return usersRepository.save(userInfo);
            } else {
                throw new EntityAlreadyExistsException("UserInfo with username:{} already esits");
            }
        }
    }

    private UserInfo copyUserInfo(UserInfo userInfo, UserInfo userInfoFromDb) {
        final AuthenticationInfo authenticationInfo = userInfo.getAuthenticationInfo();

        userInfoFromDb.setPassword(userInfo.getPassword());
        userInfoFromDb.setEnabled(userInfo.isEnabled());
        userInfoFromDb.setAuthenticationInfo(authenticationInfo);
        if (authenticationInfo != null) {
            authenticationInfo.setUserInfo(userInfoFromDb);
        }
        userInfoFromDb.setRoles(userInfo.getRoles());

        return userInfoFromDb;
    }
}
