package ro.rasel.server.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.rasel.server.security.repository.model.UserInfo;

public interface UsersRepository extends JpaRepository<UserInfo, String> {
}
