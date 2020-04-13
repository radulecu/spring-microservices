package ro.rasel.spring.microservices.ssoauthservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.rasel.spring.microservices.ssoauthservice.domain.UserInfo;

public interface UsersRepository extends JpaRepository<UserInfo, String> {
}
