package ro.rasel.spring.microserivces.ssoauthservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.rasel.spring.microserivces.ssoauthservice.repository.model.UserInfoImpl;

public interface UsersRepository extends JpaRepository<UserInfoImpl, String> {
}
