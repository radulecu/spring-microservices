package ro.rasel.spring.microservices.ssoauthservice.repository.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Entity(name = "user_info")
public class UserInfoImpl implements UserInfo {
    private String userName;
    private String password;
    private boolean enabled;
    private List<Role> roles;

    public UserInfoImpl() {
    }

    public UserInfoImpl(String userName, String password, boolean enabled) {
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
    }

    @Override
    @Id
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    @Column
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    @Column
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    @OneToMany(targetEntity = RoleImpl.class, mappedBy = "userName", fetch = FetchType.EAGER)
    public List<Role> getRoles() {
        return roles;
    }

    @Override
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserInfoImpl.class.getSimpleName() + "[", "]")
                .add("userName='" + userName + "'")
                .add("password='" + password + "'")
                .add("enabled=" + enabled)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserInfoImpl userInfo = (UserInfoImpl) o;
        return enabled == userInfo.enabled &&
                Objects.equals(userName, userInfo.userName) &&
                Objects.equals(password, userInfo.password) &&
                Objects.equals(roles, userInfo.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password, enabled, roles);
    }
}
