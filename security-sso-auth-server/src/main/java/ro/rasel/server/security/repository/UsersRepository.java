package ro.rasel.server.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.rasel.server.security.repository.model.UserInfoImpl;

public interface UsersRepository extends JpaRepository<UserInfoImpl, String> {
}
