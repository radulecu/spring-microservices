package ro.rasel.server.security.repository;

import org.springframework.security.core.userdetails.User;

public interface UserDetailsRepository {
    User findUserByName(String username);
}
