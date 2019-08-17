package ro.rasel.spring.microserivces.ssoauthservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.rasel.spring.microserivces.ssoauthservice.repository.UsersRepository;
import ro.rasel.spring.microserivces.ssoauthservice.repository.model.Role;
import ro.rasel.spring.microserivces.ssoauthservice.repository.model.UserInfo;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UsersRepository usersRepository;

    @Autowired
    public UserDetailServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findById(username).map(UserDetailServiceImpl::toUserDetails)
                .orElse(new User(username, "", true, true, true, true, Collections.emptyList()));
    }

    private static UserDetails toUserDetails(UserInfo userInfo) {
        final List<String> roles =
                userInfo.getRoles().stream().map(Role::getRole).collect(Collectors.toList());
        return new User(userInfo.getUserName(), userInfo.getPassword(),
                userInfo.isEnabled(), userInfo.isEnabled(), userInfo.isEnabled(),
                userInfo.isEnabled(),
                AuthorityUtils.createAuthorityList(roles.toArray(new String[roles.size()])));
    }
}
