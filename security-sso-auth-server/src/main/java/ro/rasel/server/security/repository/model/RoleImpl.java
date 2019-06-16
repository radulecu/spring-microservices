package ro.rasel.server.security.repository.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;
import java.util.StringJoiner;

@Entity(name = "role")
public class RoleImpl implements Role {
    @Id
    String userName;
    String role;

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
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
        RoleImpl role1 = (RoleImpl) o;
        return Objects.equals(userName, role1.userName) &&
                Objects.equals(role, role1.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, role);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RoleImpl.class.getSimpleName() + "[", "]")
                .add("userName='" + userName + "'")
                .add("role='" + role + "'")
                .toString();
    }
}
