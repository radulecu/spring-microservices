package ro.rasel.server.security.repository.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
public class Role {
    @Id
    String userName;
    String role;

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
        return Objects.equals(userName, role1.userName) &&
                Objects.equals(role, role1.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, role);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Role.class.getSimpleName() + "[", "]")
                .add("userName='" + userName + "'")
                .add("role='" + role + "'")
                .toString();
    }
}
