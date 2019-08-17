package ro.rasel.spring.microserivces.ssoauthservice.repository.model;

import java.util.List;

public interface UserInfo {
    String getUserName();

    void setUserName(String userName);

    String getPassword();

    void setPassword(String password);

    boolean isEnabled();

    void setEnabled(boolean enabled);

    List<Role> getRoles();

    void setRoles(List<Role> roles);
}
