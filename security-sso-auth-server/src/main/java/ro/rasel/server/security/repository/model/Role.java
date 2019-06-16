package ro.rasel.server.security.repository.model;

public interface Role {
    String getUserName();

    void setUserName(String userName);

    String getRole();

    void setRole(String role);
}
