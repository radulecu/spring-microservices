package ro.rasel.spring.microservices.ssoauthservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.rasel.spring.microservices.ssoauthservice.repository.model.UserInfoImpl;

public interface UsersRepository extends JpaRepository<UserInfoImpl, String> {
}
