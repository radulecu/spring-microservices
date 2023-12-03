package ro.rasel.spring.microservices.ssoauthservice.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;
import java.util.StringJoiner;

import static javax.persistence.GenerationType.IDENTITY;

@Entity(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column
    private String userName;
    @Column
    private String role;

    public Integer getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role role1 = (Role) o;
        return Objects.equals(id, role1.id) &&
                Objects.equals(userName, role1.userName) &&
                Objects.equals(role, role1.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, role);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Role.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("userName='" + userName + "'")
                .add("role='" + role + "'")
                .toString();
    }

}
