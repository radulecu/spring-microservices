package ro.rasel.spring.microservices.ssoauthservice.repository.model;

public interface Role {
    String getUserName();

    void setUserName(String userName);

    String getRole();

    void setRole(String role);
}
