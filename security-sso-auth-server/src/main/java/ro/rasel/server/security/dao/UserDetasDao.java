package ro.rasel.server.security.dao;

import org.springframework.security.core.userdetails.User;

public interface UserDetasDao {
    User findUserByName(String username);
}
