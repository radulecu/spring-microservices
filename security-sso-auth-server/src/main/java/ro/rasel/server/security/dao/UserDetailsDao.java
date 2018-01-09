package ro.rasel.server.security.dao;

import org.springframework.security.core.userdetails.User;

public interface UserDetailsDao {
    User findUserByName(String username);
}
