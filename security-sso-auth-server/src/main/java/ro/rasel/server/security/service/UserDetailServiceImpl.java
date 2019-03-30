package ro.rasel.server.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.rasel.server.security.repository.UserDetailsRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserDetailsRepository userDetailsRepository;

    @Autowired
    public UserDetailServiceImpl(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDetailsRepository.findUserByName(username);
    }
}
