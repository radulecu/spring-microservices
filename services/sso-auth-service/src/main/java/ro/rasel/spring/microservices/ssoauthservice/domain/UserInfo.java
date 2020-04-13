package ro.rasel.spring.microservices.ssoauthservice.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Entity(name = "user_info")
public class UserInfo {
    @Id
    @Column
    private String userName;
    @Column
    private String password;
    @Column
    private boolean enabled;
    @OneToOne(mappedBy = "userInfo", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private AuthenticationInfo authenticationInfo;
    @OneToMany(targetEntity = Role.class, mappedBy = "userName", fetch = FetchType.EAGER)
    private List<Role> roles;

    public UserInfo() {
    }

    public UserInfo(String userName, String password, boolean enabled) {
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public AuthenticationInfo getAuthenticationInfo() {
        return authenticationInfo;
    }

    public void setAuthenticationInfo(AuthenticationInfo authenticationInfo) {
        this.authenticationInfo = authenticationInfo;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserInfo userInfo = (UserInfo) o;
        return enabled == userInfo.enabled &&
                Objects.equals(userName, userInfo.userName) &&
                Objects.equals(password, userInfo.password) &&
                Objects.equals(authenticationInfo, userInfo.authenticationInfo) &&
                Objects.equals(roles, userInfo.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password, enabled, authenticationInfo, roles);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserInfo.class.getSimpleName() + "[", "]")
                .add("userName='" + userName + "'")
                .add("password='" + password + "'")
                .add("enabled=" + enabled)
                .add("authenticationInfo=" + authenticationInfo)
                .add("roles=" + roles)
                .toString();
    }

}
