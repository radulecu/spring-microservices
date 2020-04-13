package ro.rasel.spring.microservices.ssoauthservice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "authentication_info")
public class AuthenticationInfo {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    Integer id;
    @Column
    private LocalDateTime lockedUntil;
    @Column
    private int tries;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userName")
    private UserInfo userInfo;

    public AuthenticationInfo() {
    }

    public Integer getId() {
        return id;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public LocalDateTime getLockedUntil() {
        return lockedUntil;
    }

    public void setLockedUntil(LocalDateTime lockedUntil) {
        this.lockedUntil = lockedUntil;
    }

    public int getTries() {
        return tries;
    }

    public void setTries(int tries) {
        this.tries = tries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuthenticationInfo that = (AuthenticationInfo) o;
        return tries == that.tries &&
                Objects.equals(id, that.id) &&
                Objects.equals(lockedUntil, that.lockedUntil) &&
                Objects.equals(userInfo, that.userInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lockedUntil, tries, userInfo);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AuthenticationInfo.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("lockedUntil=" + lockedUntil)
                .add("tries=" + tries)
                .add("userInfo=" + userInfo)
                .toString();
    }

}
